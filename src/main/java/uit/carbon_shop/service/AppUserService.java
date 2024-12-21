package uit.carbon_shop.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uit.carbon_shop.model.AppUserDTO;
import uit.carbon_shop.util.ReferencedWarning;


public interface AppUserService {

    Page<AppUserDTO> findAll(String filter, Pageable pageable);

    AppUserDTO get(UUID userId);

    UUID create(AppUserDTO appUserDTO);

    void update(UUID userId, AppUserDTO appUserDTO);

    void delete(UUID userId);

    boolean companyExists(UUID id);

    ReferencedWarning getReferencedWarning(UUID userId);

}
