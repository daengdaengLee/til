package example.validator;

import example.annotation.SimpleAnnotation;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class SimpleAnnotatedValidator {
    public boolean validate(Object object) {
        return Arrays.stream(object.getClass().getAnnotations())
                .anyMatch(this::isTargetAnnotation);
    }

    private boolean isTargetAnnotation(Annotation annotation) {
        return annotation instanceof SimpleAnnotation;
    }
}
