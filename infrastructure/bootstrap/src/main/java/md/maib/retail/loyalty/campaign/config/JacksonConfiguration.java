package md.maib.retail.loyalty.campaign.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
@Slf4j
class JacksonConfiguration {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
        return builder -> builder.serializationInclusion(NON_NULL)
                .failOnUnknownProperties(false)
                .featuresToEnable(ACCEPT_CASE_INSENSITIVE_ENUMS)
                .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS)
                .findModulesViaServiceLoader(true);
    }
}