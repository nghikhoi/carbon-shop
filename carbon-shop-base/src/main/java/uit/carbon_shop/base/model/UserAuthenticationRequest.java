package uit.carbon_shop.base.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import uit.carbon_shop.base.util.WebUtils;


@Getter
@Setter
public class UserAuthenticationRequest {

    @NotNull
    @Size(max = 255)
    @Email(regexp = WebUtils.EMAIL_PATTERN)
    private String password;

    @NotNull
    @Size(max = 72)
    private String password;

}
