package md.maib.retail;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

class HealthTest extends BaseComponentTest {

    @ParameterizedTest
    @ValueSource(strings = {"livez", "readyz"})
    void exposure(String group) {
        var response = restTemplate.getForEntity("/{group}", String.class, group);
        AssertionsHelper.assertExpectedResponse(response, HttpStatus.OK, "{\"status\":\"UP\"}");
    }
}