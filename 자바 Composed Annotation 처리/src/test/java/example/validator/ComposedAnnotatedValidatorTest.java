package example.validator;

import example.target.ComposedAnnotated;
import example.target.SimpleAnnotated;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComposedAnnotatedValidatorTest {
    @Test
    void validateSimpleAnnotated() {
        // given
        var target = new SimpleAnnotated();
        var validator = new ComposedAnnotatedValidator();

        // when
        var result = validator.validate(target);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void validateComposedAnnotated() {
        // given
        var target = new ComposedAnnotated();
        var validator = new ComposedAnnotatedValidator();

        // when
        var result = validator.validate(target);

        // then
        // SUCCESS! : ComposedAnnotatedValidator can find out SimpleAnnotation in ComposedAnnotation.
        assertThat(result).isTrue();
    }
}