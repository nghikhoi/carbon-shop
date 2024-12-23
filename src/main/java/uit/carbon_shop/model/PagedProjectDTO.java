package uit.carbon_shop.model;

import org.springframework.data.domain.Page;

public class PagedProjectDTO extends PagedModel<ProjectDTO> {

    public PagedProjectDTO(Page<ProjectDTO> delegate) {
        super(delegate);
    }

}
