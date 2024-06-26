package md.maib.retail;


import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Configuration
@DependsOn("service")
class ComponentTestsConfig {

    @Value("${embedded.service.host}")
    String host;

    @Value("${embedded.service.port}")
    int port;

    @Bean
    TestRestTemplate testRestTemplate() {
        var builder = new RestTemplateBuilder();
        var restTemplate = new TestRestTemplate(builder);

        var handler = new RootUriTemplateHandler("http://%s:%s".formatted(host, port));
        restTemplate.setUriTemplateHandler(handler);

        restTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());

        return restTemplate;
    }

    @Bean
    ParameterNamesModule parameterNamesModule() {
        return new ParameterNamesModule(Mode.PROPERTIES);
    }
}