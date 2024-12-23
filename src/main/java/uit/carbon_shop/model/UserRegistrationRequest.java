package uit.carbon_shop.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import uit.carbon_shop.util.WebUtils;


@Getter
@Setter
public class UserRegistrationRequest {

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

    @Size(max = 255)
    private String companyName;

    @Size(max = 255)
    private String companyAddress;

    @Size(max = 255)
    private String companyTaxCode;

    @Size(max = 255)
    private String companyIndustry;

    @Size(max = 255)
    private String companyPhone;

    @Size(max = 255)
    @Email(regexp = WebUtils.EMAIL_PATTERN)
    @RegistrationRequestEmailUnique
    private String companyEmail;

}
