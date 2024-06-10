package md.maib.retail.loyalty.campaign.testcontainers;

import lombok.SneakyThrows;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.annotation.Order;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Order(HIGHEST_PRECEDENCE)
public class ImageBuilderTestExecutionListener implements SpringApplicationRunListener {

    private final boolean buildImage;

    public ImageBuilderTestExecutionListener(SpringApplication application, String[] args) {
        buildImage = Arrays.asList(args).contains("build.image=true");
    }

    @SneakyThrows
    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        if (buildImage) {
            var invoker = new DefaultInvoker();

            var request = new DefaultInvocationRequest();
            request.setPomFile(new File("../pom.xml"));
            request.setGoals(List.of("package -DskipTests=true -Dsha1=00000000"));
            invoker.setMavenHome(Path.of(".").toFile());
            invoker.setMavenExecutable(new File("./mvnw"));

            var result = invoker.execute(request);

            if (result.getExitCode() != 0) {
                if (result.getExecutionException() != null) {
                    throw new IllegalStateException("Build failed. Exist code: " + result.getExitCode(), result.getExecutionException());
                } else {
                    throw new IllegalStateException("Build failed. Exist code: " + result.getExitCode());
                }
            }
        }
    }
}