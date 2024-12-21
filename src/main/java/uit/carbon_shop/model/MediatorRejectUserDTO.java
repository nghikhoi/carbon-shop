package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MediatorRejectUserDTO {

    private UUID userId;

    @Size(max = 255)
    private String message;

}
