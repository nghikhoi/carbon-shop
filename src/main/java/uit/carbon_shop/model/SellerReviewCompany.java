package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SellerReviewCompany {

    @Size(max = 255)
    private String message;

    private Integer rate;

    private List<Long> images;
}
