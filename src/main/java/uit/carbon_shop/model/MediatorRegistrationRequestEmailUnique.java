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
import uit.carbon_shop.service.MediatorRegistrationService;


/**
 * Validate that the email value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = MediatorRegistrationRequestEmailUnique.MediatorRegistrationRequestEmailUniqueValidator.class
)
public @interface MediatorRegistrationRequestEmailUnique {

    String message() default "{registration.register.taken}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MediatorRegistrationRequestEmailUniqueValidator implements ConstraintValidator<MediatorRegistrationRequestEmailUnique, String> {

        private final MediatorRegistrationService mediatorRegistrationService;

        public MediatorRegistrationRequestEmailUniqueValidator(
                final MediatorRegistrationService mediatorRegistrationService) {
            this.mediatorRegistrationService = mediatorRegistrationService;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            return !mediatorRegistrationService.emailExists(value);
        }

    }

}
