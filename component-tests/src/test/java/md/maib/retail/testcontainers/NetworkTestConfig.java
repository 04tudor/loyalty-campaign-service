package md.maib.retail.testcontainers;

import com.playtika.testcontainer.common.spring.DockerPresenceBootstrapConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.testcontainers.containers.Network;

@Slf4j
@Configuration
@ConditionalOnProperty(
        name = "embedded.service.enabled",
        havingValue = "true",
        matchIfMissing = true)
@AutoConfigureAfter(DockerPresenceBootstrapConfiguration.class)
@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
public class NetworkTestConfig {

    @Bean
    @ConditionalOnMissingBean(Network.class)
    public Network network() {
        var network = Network.SHARED;
        log.info("✓ Created container Network id={}", network.getId());
        return network;
    }
}