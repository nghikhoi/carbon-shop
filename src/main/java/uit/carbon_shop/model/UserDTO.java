package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private UUID userId;

    @Size(max = 255)
    private String companyName;

    @Size(max = 255)
    private String companyEmail;

    @Size(max = 255)
    private String companyAddress;

    @Size(max = 255)
    private String companyTaxCode;

    @Size(max = 255)
    private String password;

    @Size(max = 255)
    private String companySector;

    @Size(max = 255)
    private String companyAgent;

    @Size(max = 255)
    private String companyAgentPhoneNumber;

    private Long reviewCompanyId;

}
