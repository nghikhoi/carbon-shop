package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContractDTO {

    private Long constractId;

    @Size(max = 255)
    private String constractFile;

    @Size(max = 255)
    private String dateSign;

}
