package uit.carbon_shop.base.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uit.carbon_shop.base.model.UserDTO;
import uit.carbon_shop.base.util.ReferencedWarning;


public interface UserService {

    Page<UserDTO> findAll(String filter, Pageable pageable);

    UserDTO get(UUID userId);

    UUID create(UserDTO userDTO);

    void update(UUID userId, UserDTO userDTO);

    void delete(UUID userId);

    boolean companyExists(UUID id);

    ReferencedWarning getReferencedWarning(UUID userId);

}
