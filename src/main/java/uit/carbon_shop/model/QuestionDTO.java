package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuestionDTO {

    private Long id;

    @Size(max = 255)
    private String question;

    @Size(max = 255)
    private String answer;

    private Long askedBy;

}
