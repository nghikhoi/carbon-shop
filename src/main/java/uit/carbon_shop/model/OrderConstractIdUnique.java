package uit.carbon_shop.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;
import uit.carbon_shop.service.OrderService;


/**
 * Validate that the constractId value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = OrderConstractIdUnique.OrderConstractIdUniqueValidator.class
)
public @interface OrderConstractIdUnique {

    String message() default "{Exists.order.constractId}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class OrderConstractIdUniqueValidator implements ConstraintValidator<OrderConstractIdUnique, Long> {

        private final OrderService orderService;
        private final HttpServletRequest request;

        public OrderConstractIdUniqueValidator(final OrderService orderService,
                final HttpServletRequest request) {
            this.orderService = orderService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("orderId");
            if (currentId != null && value.equals(orderService.get(Long.parseLong(currentId)).getConstractId())) {
                // value hasn't changed
                return true;
            }
            return !orderService.constractIdExists(value);
        }

    }

}
