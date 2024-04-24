package md.maib.retail.junit;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Tag("unit")

public @interface UnitTest {
}
