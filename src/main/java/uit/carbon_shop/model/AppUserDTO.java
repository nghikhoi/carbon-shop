package uit.carbon_shop.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import uit.carbon_shop.util.WebUtils;


@Getter
@Setter
public class AppUserDTO {

    private UUID userId;

    @NotNull
    @Size(max = 255)
    private String password;

    @Size(max = 255)
    private String resetPasswordUid;

    private OffsetDateTime resetPasswordStart;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String phone;

    @Size(max = 255)
    @Email(regexp = WebUtils.EMAIL_PATTERN)
    private String email;

    @NotNull
    private UserRole role;

    @AppUserCompanyUnique
    private UUID company;

    private List<UUID> favoriteProjects;

}
