package md.maib.retail.loyalty.campaign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class MetricsTest extends BaseComponentTest {

    @Value("${embedded.service.management.port}")
    int managementPort;

    @Value("${embedded.service.host}")
    String host;

    @Test
    void jvmMemoryExposure() {
        var histogram = metric("jvm_memory_used_bytes")
                .and(tag("area", "heap"));

        assertThat(metrics().lines())
                .anyMatch(histogram);
    }

    String metrics() {
        return restTemplate.getForObject("http://{host}:{port}/metrics", String.class, host, managementPort);
    }

    Predicate<String> metric(String metricName) {
        return metric -> metric.contains(metricName);
    }

    Predicate<String> tag(String name, String value) {
        return metric -> metric.contains("%s=\"%s".formatted(name, value));
    }
}