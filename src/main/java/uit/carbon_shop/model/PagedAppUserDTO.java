package uit.carbon_shop.model;

import org.springframework.data.domain.Page;

public class PagedAppUserDTO extends PagedModel<AppUserDTO> {

    public PagedAppUserDTO(Page<AppUserDTO> delegate) {
        super(delegate);
    }

}
