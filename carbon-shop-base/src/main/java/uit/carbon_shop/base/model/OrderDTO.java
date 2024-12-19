package uit.carbon_shop.base.model;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long orderId;

    private Long creditAmount;

    @Size(max = 255)
    private String unit;

    @Size(max = 255)
    private String price;

    @Size(max = 255)
    private String total;

    private OrderStatus status;

    private UUID project;

    private UUID processBy;

    private UUID createdBy;

}
