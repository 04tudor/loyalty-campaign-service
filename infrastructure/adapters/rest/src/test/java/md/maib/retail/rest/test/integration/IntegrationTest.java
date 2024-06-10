package md.maib.retail.rest.test.integration;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Tag("integration")
@SpringBootTest(webEnvironment = RANDOM_PORT, args = "build.image=false")
@TestPropertySource("classpath:integration-tests.properties")
public @interface IntegrationTest {

}

