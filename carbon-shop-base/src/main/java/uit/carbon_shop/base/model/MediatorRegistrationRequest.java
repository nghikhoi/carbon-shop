package uit.carbon_shop.base.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import uit.carbon_shop.base.util.WebUtils;


@Getter
@Setter
public class MediatorRegistrationRequest {

    @NotNull
    @Size(max = 255)
    @Email(regexp = WebUtils.EMAIL_PATTERN)
    @MediatorRegistrationRequestPasswordUnique
    private String password;

    @NotNull
    @Size(max = 72)
    @MediatorRegistrationRequestPasswordUnique
    private String password;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String phone;

    @Size(max = 255)
    private String email;

}
