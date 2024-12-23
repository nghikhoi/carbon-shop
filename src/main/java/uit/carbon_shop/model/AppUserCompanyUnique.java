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
import uit.carbon_shop.service.AppUserService;


/**
 * Validate that the id value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = AppUserCompanyUnique.AppUserCompanyUniqueValidator.class
)
public @interface AppUserCompanyUnique {

    String message() default "{Exists.appUser.company}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class AppUserCompanyUniqueValidator implements ConstraintValidator<AppUserCompanyUnique, Long> {

        private final AppUserService appUserService;
        private final HttpServletRequest request;

        public AppUserCompanyUniqueValidator(final AppUserService appUserService,
                final HttpServletRequest request) {
            this.appUserService = appUserService;
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
            final String currentId = pathVariables.get("userId");
            if (currentId != null && value.equals(appUserService.get(Long.parseLong(currentId)).getCompany())) {
                // value hasn't changed
                return true;
            }
            return !appUserService.companyExists(value);
        }

    }

}
