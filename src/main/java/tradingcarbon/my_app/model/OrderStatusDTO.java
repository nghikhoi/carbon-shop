package tradingcarbon.my_app.model;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderStatusDTO {

    private Long orderStatusId;

    @Size(max = 255)
    private String buyerId;

    @Size(max = 255)
    private String dateCreate;

    @Size(max = 255)
    private String orderStatus;

    private UUID projectId;

}
