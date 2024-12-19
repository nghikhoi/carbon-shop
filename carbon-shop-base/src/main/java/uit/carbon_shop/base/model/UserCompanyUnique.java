package uit.carbon_shop.base.model;

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
import java.util.UUID;
import org.springframework.web.servlet.HandlerMapping;
import uit.carbon_shop.base.service.UserService;


/**
 * Validate that the id value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = UserCompanyUnique.UserCompanyUniqueValidator.class
)
public @interface UserCompanyUnique {

    String message() default "{Exists.user.company}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UserCompanyUniqueValidator implements ConstraintValidator<UserCompanyUnique, UUID> {

        private final UserService userService;
        private final HttpServletRequest request;

        public UserCompanyUniqueValidator(final UserService userService,
                final HttpServletRequest request) {
            this.userService = userService;
            this.request = request;
        }

        @Override
        public boolean isValid(final UUID value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("userId");
            if (currentId != null && value.equals(userService.get(UUID.fromString(currentId)).getCompany())) {
                // value hasn't changed
                return true;
            }
            return !userService.companyExists(value);
        }

    }

}
