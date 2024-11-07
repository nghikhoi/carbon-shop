package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StaffDTO {

    private Long staffId;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String password;

    @Size(max = 255)
    private String staffName;

    @Size(max = 255)
    private String staffPhone;

}
