package md.maib.retail.testcontainers;

import org.apache.maven.shared.invoker.*;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.annotation.Order;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Order(HIGHEST_PRECEDENCE)
public class ImageBuilderTestExecutionListener implements SpringApplicationRunListener {

    private static boolean alreadyBuilt = false;
    private boolean buildImage = false;
    private String mvnwPath = "./mvnw";
    private String mvnwHome = ".";
    private String pomPath = "./pom.xml";
    private boolean repeatable = true;

    public ImageBuilderTestExecutionListener(String[] args) {
        List<String> argsList = Arrays.asList(args);
        findArg(argsList, "build.image").ifPresent(path -> buildImage = "true".equalsIgnoreCase(path));
        findArg(argsList, "build.mvnw.path").ifPresent(path -> mvnwPath = path);
        findArg(argsList, "build.mvnw.home").ifPresent(path -> mvnwHome = path);
        findArg(argsList, "build.pom.path").ifPresent(path -> pomPath = path);
        findArg(argsList, "build.repeatable").ifPresent(type -> repeatable = "true".equalsIgnoreCase(type));
    }

    private static synchronized void setAsAlreadyBuilt() {
        alreadyBuilt = true;
    }

    private Optional<String> findArg(List<String> argsList, String key) {
        return argsList.stream()
                .filter(arg -> arg.startsWith("%s=".formatted(key)))
                .map(arg -> arg.split("=")[1])
                .findFirst();
    }

    private boolean shouldBuildImage() {
        if (buildImage) {
            if (repeatable) {
                return true;
            }
            return !alreadyBuilt;
        }
        return false;
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        if (shouldBuildImage()) {
            invoke(buildInvoker(), buildRequest());
            setAsAlreadyBuilt();
        }
    }

    private Invoker buildInvoker() {
        var invoker = new DefaultInvoker();
        invoker.setMavenHome(Path.of(mvnwHome).toFile());
        invoker.setMavenExecutable(new File(mvnwPath));
        return invoker;
    }

    private InvocationRequest buildRequest() {
        var request = new DefaultInvocationRequest();
        request.setPomFile(new File(pomPath));

        // Forward jib properties to mvn goal
        String jibProperties = System.getProperties().entrySet()
                .stream()
                .filter(p -> p.getKey().toString().startsWith("jib"))
                .map(p -> "-D%s=\"%s\"".formatted(p.getKey(), p.getValue()))
                .collect(joining(" "));

        request.setGoals(List.of("package -DskipTests=true -Djib.allowInsecureRegistries -Drevision=build -Dsha1=build " + jibProperties));
        return request;
    }

    private void invoke(Invoker invoker, InvocationRequest request) {
        InvocationResult result;
        try {
            result = invoker.execute(request);
        } catch (MavenInvocationException e) {
            throw new RuntimeException(e);
        }

        if (result.getExitCode() != 0) {
            if (result.getExecutionException() != null) {
                throw new IllegalStateException("Build failed. Exist code: " + result.getExitCode(), result.getExecutionException());
            } else {
                throw new IllegalStateException("Build failed. Exist code: " + result.getExitCode());
            }
        }
    }
}