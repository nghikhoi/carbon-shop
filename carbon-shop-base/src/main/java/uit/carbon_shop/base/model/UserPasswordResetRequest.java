package uit.carbon_shop.base.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import uit.carbon_shop.base.util.WebUtils;


@Getter
@Setter
public class UserPasswordResetRequest {

    @NotNull
    @Size(max = 255)
    @Email(regexp = WebUtils.EMAIL_PATTERN)
    @UserPasswordResetRequestPasswordExists
    private String email;

}
