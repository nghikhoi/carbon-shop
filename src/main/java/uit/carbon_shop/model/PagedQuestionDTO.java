package uit.carbon_shop.model;

import org.springframework.data.domain.Page;

public class PagedQuestionDTO extends PagedModel<QuestionDTO> {

    public PagedQuestionDTO(Page<QuestionDTO> delegate) {
        super(delegate);
    }

}
