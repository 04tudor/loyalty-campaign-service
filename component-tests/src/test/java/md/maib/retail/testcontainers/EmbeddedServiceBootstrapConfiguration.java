package md.maib.retail.testcontainers;

import com.github.dockerjava.api.DockerClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.http.HttpStatus;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.util.Map;

import static com.playtika.testcontainer.common.utils.ContainerUtils.configureCommonsAndStart;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;
import static org.testcontainers.containers.wait.strategy.Wait.forHttp;

@Slf4j
@Configuration
@ConditionalOnExpression("${embedded.containers.enabled:true}")
@ConditionalOnProperty(
        name = "embedded.service.enabled",
        havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties(ServiceProperties.class)
public class EmbeddedServiceBootstrapConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger("Container");

    private static final String SERVICE_NETWORK_ALIAS = "service.testcontainer.docker";

    @Bean(destroyMethod = "stop")
    public ServiceContainer service(
            ConfigurableEnvironment environment,
            ServiceProperties properties,
            Network network) {

        var service = new ServiceContainer(properties.getDockerImage(), properties.isClearImage())
                .withEnv(systemEnv())
                .withExposedPorts(properties.getPort(), properties.getManagementPort(), properties.getDebugPort())
                .withNetworkAliases(SERVICE_NETWORK_ALIAS)
                .withNetwork(network)
                .withLogConsumer(new Slf4jLogConsumer(LOGGER).withPrefix(properties.getDockerImage()))
                .waitingFor(forHttp(properties.getReadyzPath())
                        .forPort(properties.getPort())
                        .forStatusCode(HttpStatus.OK.value()));

        if (properties.getExposedPorts() != null && properties.getExposedPorts().length > 0) {
            service.withAccessToHost(true);
            Testcontainers.exposeHostPorts(properties.getExposedPorts());
        }

        service = (ServiceContainer) configureCommonsAndStart(service, properties, log);

        registerServiceEnvironment(service, environment, properties);

        return service;
    }

    private Map<String, String> systemEnv() {
        return System.getenv().entrySet()
                .stream()
                // Filtering system envs that have no sense to application
                .filter(e -> e.getKey().matches("^\\w.+"))
                .filter(e -> !e.getKey().equals("PATH"))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void registerServiceEnvironment(
            ServiceContainer service,
            ConfigurableEnvironment environment,
            ServiceProperties properties) {

        var host = service.getHost();
        var port = service.getMappedPort(properties.getPort());
        var managementPort = service.getMappedPort(properties.getManagementPort());
        var debugPort = service.getMappedPort(properties.getDebugPort());

        var params = Map.<String, Object>ofEntries(
                entry("embedded.service.host", host),
                entry("embedded.service.port", port),
                entry("embedded.service.management.port", managementPort),
                entry("embedded.service.debug.port", debugPort),
                entry("embedded.service.networkAlias", SERVICE_NETWORK_ALIAS)
        );

        log.info("Started {}. Connection Details: {}, Connection URI: http://{}:{}, NetworkAlias: {}, Debug port: {}",
                properties.getDockerImage(), params, host, port, SERVICE_NETWORK_ALIAS, debugPort);

        var propertySource = new MapPropertySource("embeddedServiceInfo", params);
        environment.getPropertySources().addFirst(propertySource);
    }

    private static class ServiceContainer extends GenericContainer<ServiceContainer> {

        private final boolean clearImage;

        public ServiceContainer(String dockerImageName, boolean clearImage) {
            super(dockerImageName);
            this.clearImage = clearImage;
        }

        @Override
        public void stop() {
            super.stop();

            if (clearImage) {
                String imageName = getDockerImageName();

                DockerClient dockerClient = DockerClientFactory.lazyClient();
                dockerClient.removeImageCmd(imageName).exec();

                log.info("Image {} was successfully removed", imageName);
            }
        }
    }
}