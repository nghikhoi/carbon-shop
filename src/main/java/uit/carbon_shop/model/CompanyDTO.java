package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CompanyDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String taxCode;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String industry;

    private CompanyStatus status;

}
