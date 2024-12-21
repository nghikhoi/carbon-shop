package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MediatorRejectProjectDTO {

    private Long projectId;

    @Size(max = 255)
    private String message;

}
