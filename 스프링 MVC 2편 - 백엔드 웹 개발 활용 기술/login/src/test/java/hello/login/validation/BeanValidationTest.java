package hello.login.validation;

import hello.login.web.item.form.ItemSaveForm;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class BeanValidationTest {
    @Test
    void beanValidation() {
        Locale.setDefault(Locale.KOREAN);

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();

            var item = new ItemSaveForm();
            item.setItemName(" "); // 공백
            item.setPrice(0);
            item.setQuantity(10000);

            var violations = validator.validate(item);
            for (var violation : violations) {
                System.out.println("violation = " + violation);
                System.out.println("violation.message = " + violation.getMessage());
            }
        }
    }
}
