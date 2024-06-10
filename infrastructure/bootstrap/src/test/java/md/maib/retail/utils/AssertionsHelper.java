package md.maib.retail.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@UtilityClass
public class AssertionsHelper {

    public static void assertExpectedResponse(ResponseEntity<String> response, HttpStatus expectedStatus, String expectedBody) {
        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode())
                    .as("check status code")
                    .isEqualTo(expectedStatus);

            softly.assertThat(response.getBody())
                    .as("check body")
                    .isEqualTo(expectedBody);
        });
    }
}