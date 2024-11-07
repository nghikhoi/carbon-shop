package tradingcarbon.my_app.model;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentDTO {

    private UUID paymentId;

    @Size(max = 255)
    private String datePayment;

    @Size(max = 255)
    private String total;

    @Size(max = 255)
    private String paymentNumber;

}
