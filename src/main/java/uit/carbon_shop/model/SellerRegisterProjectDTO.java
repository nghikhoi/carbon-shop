package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SellerRegisterProjectDTO {

    private String name;

    private String address;

    @Size(max = 255)
    private String size;

    private LocalDateTime timeStart;

    private LocalDateTime timeEnd;

    @Size(max = 255)
    private String produceCarbonRate;

    @Size(max = 255)
    private String partner;

    @Size(max = 255)
    private String auditByOrg;

    private Long creditAmount;

    @Size(max = 255)
    private String cert;

    @Size(max = 255)
    private String price;

    @Size(max = 255)
    private String methodPayment;

    private List<Long> projectImages;

}
