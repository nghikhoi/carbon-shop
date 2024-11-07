package tradingcarbon.my_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProjectDTO {

    private UUID projectId;

    @Size(max = 255)
    private String projectName;

    @Size(max = 255)
    private String projectAddress;

    @Size(max = 255)
    private String projectSize;

    @Size(max = 255)
    private String projectTimeStart;

    @Size(max = 255)
    private String projectTimeEnd;

    @Size(max = 255)
    private String projectRangeCarbon;

    @Size(max = 255)
    private String organizationProvide;

    @Size(max = 255)
    private String numberCarBonCredit;

    @Size(max = 255)
    private String creditTimeStart;

    @Size(max = 255)
    private String price;

    @Size(max = 255)
    private String methodPayment;

    @Size(max = 255)
    private String projectCredit;

    @Size(max = 255)
    private String creditDetail;

    @Size(max = 255)
    private String creditId;

    private List<@Size(max = 255) String> image;

    @NotNull
    private UUID userId;

    private Long reviewProjectId;

}
