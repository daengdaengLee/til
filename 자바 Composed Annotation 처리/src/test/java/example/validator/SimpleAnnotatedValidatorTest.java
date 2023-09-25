package example.validator;

import example.target.ComposedAnnotated;
import example.target.SimpleAnnotated;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleAnnotatedValidatorTest {
    @Test
    void validateSimpleAnnotated() {
        // given
        var target = new SimpleAnnotated();
        var validator = new SimpleAnnotatedValidator();

        // when
        var result = validator.validate(target);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void validateComposedAnnotated() {
        // given
        var target = new ComposedAnnotated();
        var validator = new SimpleAnnotatedValidator();

        // when
        var result = validator.validate(target);

        // then
        // FAIL! : SimpleAnnotatedValidator cannot find out SimpleAnnotation in ComposedAnnotation.
        assertThat(result).isTrue();
    }
}