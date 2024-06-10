package md.maib.retail.loyalty.campaign;

import md.maib.retail.loyalty.campaign.testcontainers.EmbeddedServiceBootstrapConfiguration;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Tag("component")
@SpringBootTest(
        webEnvironment = WebEnvironment.NONE
)
@TestPropertySource({"classpath:component-tests.properties"})
@ContextConfiguration
public @interface ComponentTest {
    @AliasFor(
            annotation = SpringBootTest.class,
            attribute = "args"
    )
    String[] args() default {"build.image=true"};

    @AliasFor(
            annotation = ContextConfiguration.class,
            attribute = "classes"
    )
    Class[] contextClasses() default {ComponentTestsConfig.class, EmbeddedServiceBootstrapConfiguration.class};
}