package uit.carbon_shop.model;

import org.springframework.data.domain.Page;

public class PagedOrderDTO extends PagedModel<OrderDTO> {

    public PagedOrderDTO(Page<OrderDTO> delegate) {
        super(delegate);
    }

}
