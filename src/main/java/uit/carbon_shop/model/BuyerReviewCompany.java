package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BuyerReviewCompany {

    @Size(max = 255)
    private String content;

}
