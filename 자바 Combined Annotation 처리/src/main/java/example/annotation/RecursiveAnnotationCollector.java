package example.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RecursiveAnnotationCollector {
    public List<Annotation> collect(Object object) {
        var result = new ArrayList<Annotation>();
        var queue = new LinkedList<Annotation>();
        Arrays.stream(object.getClass().getAnnotations())
                .filter(this::isTargetAnnotation)
                .forEach(annotation -> {
                    result.add(annotation);
                    queue.add(annotation);
                });
        while (!queue.isEmpty()) {
            var annotation = queue.pop();
            Arrays.stream(annotation.annotationType().getAnnotations())
                    .filter(this::isTargetAnnotation)
                    .forEach(nestedAnnotation -> {
                        result.add(nestedAnnotation);
                        queue.add(nestedAnnotation);
                    });
        }
        return result;
    }

    private boolean isTargetAnnotation(Annotation annotation) {
        return annotation instanceof SimpleA ||
                annotation instanceof SimpleB ||
                annotation instanceof CombinedAnnotation;
    }
}
