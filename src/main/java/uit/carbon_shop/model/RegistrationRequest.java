package uit.carbon_shop.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import uit.carbon_shop.util.WebUtils;


@Getter
@Setter
public class RegistrationRequest {

    @NotNull
    @Size(max = 72)
    private String password;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String phone;

    @NotNull
    @Size(max = 255)
    @Email(regexp = WebUtils.EMAIL_PATTERN)
    @RegistrationRequestEmailUnique
    private String email;

    private LocalDateTime approvedAt;

}
