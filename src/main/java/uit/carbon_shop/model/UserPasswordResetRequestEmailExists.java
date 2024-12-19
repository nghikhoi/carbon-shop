package uit.carbon_shop.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import uit.carbon_shop.repos.UserRepository;
import uit.carbon_shop.util.WebUtils;


/**
 * Validate that there is an account for the given e-mail.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = UserPasswordResetRequestEmailExists.UserPasswordResetRequestEmailExistsValidator.class
)
public @interface UserPasswordResetRequestEmailExists {

    String message() default "{passwordReset.start.noAccount}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UserPasswordResetRequestEmailExistsValidator implements ConstraintValidator<UserPasswordResetRequestEmailExists, String> {

        private final UserRepository userRepository;

        public UserPasswordResetRequestEmailExistsValidator(final UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null || !value.matches(WebUtils.EMAIL_PATTERN)) {
                // no valid value present
                return true;
            }
            return userRepository.existsByEmailIgnoreCase(value);
        }

    }

}
