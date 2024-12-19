package uit.carbon_shop.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import uit.carbon_shop.util.WebUtils;


@Getter
@Setter
public class MediatorPasswordResetRequest {

    @NotNull
    @Size(max = 255)
    @Email(regexp = WebUtils.EMAIL_PATTERN)
    @MediatorPasswordResetRequestEmailExists
    private String email;

}
