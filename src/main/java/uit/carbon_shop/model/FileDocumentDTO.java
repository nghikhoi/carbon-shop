package uit.carbon_shop.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FileDocumentDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String contentType;

    @Size(max = 255)
    private String contentId;

    private Long contentLength;

}
