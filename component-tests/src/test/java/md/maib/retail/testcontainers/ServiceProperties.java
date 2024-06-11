package md.maib.retail.testcontainers;

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
    private Integer debugPort = 9009;
    private String readyzPath = "/readyz";
    private Map<String, String> env = new HashMap<>();
    private int[] exposedPorts = new int[]{};
    private boolean clearImage = true;
}