package md.maib.retail.model.junit;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Tag("unit")

public @interface UnitTest {
}
