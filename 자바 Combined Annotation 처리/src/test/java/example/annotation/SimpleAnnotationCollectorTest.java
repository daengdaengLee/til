package example.annotation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleAnnotationCollectorTest {
    @Test
    void singleAnnotated() {
        // given
        var collector = new SimpleAnnotationCollector();
        var target = new SingleAnnotated();

        // when
        var annotations = collector.collect(target);

        // then
        var annotationTypes = annotations
                .stream()
                .map((Function<Annotation, Class<? extends Annotation>>) Annotation::annotationType)
                .toList();
        assertThat(annotationTypes).containsExactlyInAnyOrder(SimpleA.class);
    }

    @Test
    void multipleAnnotated() {
        // given
        var collector = new SimpleAnnotationCollector();
        var target = new MultipleAnnotated();

        // when
        var annotations = collector.collect(target);

        // then
        var annotationTypes = annotations
                .stream()
                .map((Function<Annotation, Class<? extends Annotation>>) Annotation::annotationType)
                .toList();
        assertThat(annotationTypes).containsExactlyInAnyOrder(SimpleA.class, SimpleB.class);
    }

    @Test
    void combinedAnnotated() {
        // given
        var collector = new SimpleAnnotationCollector();
        var target = new CombinedAnnotated();

        // when
        var annotations = collector.collect(target);

        // then
        var annotationTypes = annotations
                .stream()
                .map((Function<Annotation, Class<? extends Annotation>>) Annotation::annotationType)
                .toList();
        assertThat(annotationTypes).containsExactlyInAnyOrder(CombinedAnnotation.class);
    }
}