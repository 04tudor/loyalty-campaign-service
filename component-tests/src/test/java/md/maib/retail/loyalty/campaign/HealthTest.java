package md.maib.retail.loyalty.campaign;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import static md.maib.retail.loyalty.campaign.AssertionsHelper.assertExpectedResponse;

class HealthTest extends BaseComponentTest {

    @ParameterizedTest
    @ValueSource(strings = {"livez", "readyz"})
    void exposure(String group) {
        var response = restTemplate.getForEntity("/{group}", String.class, group);
        assertExpectedResponse(response, HttpStatus.OK, "{\"status\":\"UP\"}");
    }
}