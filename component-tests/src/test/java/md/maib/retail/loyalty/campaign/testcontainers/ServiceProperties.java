package md.maib.retail.loyalty.campaign.testcontainers;

import com.playtika.testcontainer.common.properties.CommonContainerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("embedded.service")
@Getter
@Setter
public class ServiceProperties extends CommonContainerProperties {

    private String dockerImage;
    private String defaultDockerImage;
    private Integer port = 8080;
    private Integer managementPort = 8081;
    private String healthPath = "/livez";
    private Map<String, String> env = new HashMap<>();
}