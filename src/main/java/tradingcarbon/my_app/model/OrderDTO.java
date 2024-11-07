package tradingcarbon.my_app.model;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long orderId;

    @Size(max = 255)
    private String numberCredits;

    @Size(max = 255)
    private String price;

    @Size(max = 255)
    private String total;

    @OrderOrderStatusIdUnique
    private Long orderStatusId;

    private UUID sellerId;

    private UUID buyerId;

    @OrderPaymentIdUnique
    private UUID paymentId;

    @OrderConstractIdUnique
    private Long constractId;

    private Long staffId;

}
