package example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SimpleA
@SimpleB
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CombinedAnnotation {
}
