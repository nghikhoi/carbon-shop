package uit.carbon_shop.base.model;

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
import uit.carbon_shop.base.service.MediatorRegistrationService;


/**
 * Validate that the password value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = MediatorRegistrationRequestPasswordUnique.MediatorRegistrationRequestPasswordUniqueValidator.class
)
public @interface MediatorRegistrationRequestPasswordUnique {

    String message() default "{registration.register.taken}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MediatorRegistrationRequestPasswordUniqueValidator implements ConstraintValidator<MediatorRegistrationRequestPasswordUnique, String> {

        private final MediatorRegistrationService mediatorRegistrationService;

        public MediatorRegistrationRequestPasswordUniqueValidator(
                final MediatorRegistrationService mediatorRegistrationService) {
            this.mediatorRegistrationService = mediatorRegistrationService;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            return !mediatorRegistrationService.passwordExists(value);
        }

    }

}
