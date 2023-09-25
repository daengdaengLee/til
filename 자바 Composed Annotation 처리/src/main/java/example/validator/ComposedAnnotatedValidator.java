package example.validator;

import example.annotation.SimpleAnnotation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class ComposedAnnotatedValidator {
    public boolean validate(Object object) {
        return Arrays.stream(object.getClass().getAnnotations())
                .flatMap(annotation -> {
                    var annotations = this.getChildAnnotations(annotation);
                    annotations.addFirst(annotation);
                    return annotations.stream();
                })
                .anyMatch(this::isTargetAnnotation);
    }

    private LinkedList<Annotation> getChildAnnotations(Annotation annotation) {
        var result = new LinkedList<Annotation>();
        Collections.addAll(result, annotation.annotationType().getAnnotations());
        return result;
    }

    private boolean isTargetAnnotation(Annotation annotation) {
        return annotation instanceof SimpleAnnotation;
    }
}
