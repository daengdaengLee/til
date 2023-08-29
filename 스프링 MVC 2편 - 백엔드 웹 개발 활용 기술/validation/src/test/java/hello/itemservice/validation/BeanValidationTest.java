package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class BeanValidationTest {
    @Test
    void beanValidation() {
        Locale.setDefault(Locale.KOREAN);

        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();

        var item = new Item();
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
