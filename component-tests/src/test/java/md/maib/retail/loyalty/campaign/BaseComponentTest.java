package md.maib.retail.loyalty.campaign;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

@ComponentTest(
        args = {
                "build.image=true",
                "build.pom.path=./../pom.xml"
        })
abstract class BaseComponentTest {

    @Autowired
    TestRestTemplate restTemplate;
}