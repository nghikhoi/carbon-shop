package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BuyerCreateOrder {

    private Long projectId;
    private Long creditAmount;

}
