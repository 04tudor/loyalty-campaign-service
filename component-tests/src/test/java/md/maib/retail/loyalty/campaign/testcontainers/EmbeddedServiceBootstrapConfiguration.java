package md.maib.retail.loyalty.campaign.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.http.HttpStatus;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Map;
import java.util.stream.Collectors;

import static com.playtika.testcontainer.common.utils.ContainerUtils.configureCommonsAndStart;
import static java.util.Map.entry;

@Slf4j
@Configuration
@ConditionalOnExpression("${embedded.containers.enabled:true}")
@Order
@ConditionalOnProperty(
        name = "embedded.service.enabled",
        havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties(ServiceProperties.class)
public class EmbeddedServiceBootstrapConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger("Container");

    @Bean(destroyMethod = "stop")
    public ServiceContainer service(ConfigurableEnvironment environment, ServiceProperties properties) {
        Map<String, String> containerEnvironment = properties.getEnv();

        containerEnvironment.putAll(System.getenv().entrySet()
                .stream()
                // Filtering system envs that have no sense to application
                .filter(e -> e.getKey().matches("^\\w.+"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        var service = new ServiceContainer(properties.getDockerImage())
                .withEnv(containerEnvironment)
                .withExposedPorts(properties.getPort(), properties.getManagementPort())

                .withLogConsumer(new Slf4jLogConsumer(LOGGER).withPrefix(properties.getDockerImage()))

                .waitingFor(Wait.forHttp(properties.getHealthPath())
                        .forPort(properties.getPort())
                        .forStatusCode(HttpStatus.OK.value()));

        service = (ServiceContainer) configureCommonsAndStart(service, properties, log);

        registerServiceEnvironment(service, environment, properties);

        return service;
    }

    private void registerServiceEnvironment(
            ServiceContainer service,
            ConfigurableEnvironment environment,
            ServiceProperties properties) {

        var host = service.getHost();
        var port = service.getMappedPort(properties.getPort());
        var managementPort = service.getMappedPort(properties.getManagementPort());

        Map<String, Object> params = Map.ofEntries(
                entry("embedded.service.host", host),
                entry("embedded.service.port", port),
                entry("embedded.service.management.port", managementPort)
        );

        log.info("Started {}. Connection Details: {}, Connection URI: http://{}:{}",
                properties.getDockerImage(), params, host, port);

        var propertySource = new MapPropertySource("embeddedServiceInfo", params);
        environment.getPropertySources().addFirst(propertySource);
    }

    private static class ServiceContainer extends GenericContainer<ServiceContainer> {

        public ServiceContainer(String dockerImageName) {
            super(dockerImageName);
        }

        @Override
        public void stop() {
            super.stop();

            String imageName = getDockerImageName();

            var dockerClient = DockerClientFactory.lazyClient();
            dockerClient.removeImageCmd(imageName).exec();

            log.info("Image {} was successfully removed", imageName);
        }
    }
}