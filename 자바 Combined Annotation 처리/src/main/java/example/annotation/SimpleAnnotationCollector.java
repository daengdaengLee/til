package example.annotation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleAnnotationCollector {
    public List<Annotation> collect(Object object) {
        return Arrays.stream(object.getClass().getAnnotations())
                .filter(this::isTargetAnnotation)
                .collect(Collectors.toList());
    }

    private boolean isTargetAnnotation(Annotation annotation) {
        return annotation instanceof SimpleA ||
                annotation instanceof SimpleB ||
                annotation instanceof CombinedAnnotation;
    }
}
