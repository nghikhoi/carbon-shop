package uit.carbon_shop.base.repos;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.base.domain.User;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findByPasswordIgnoreCase(String password);

    User findByResetPasswordUid(String resetPasswordUid);

    Page<User> findAllByUserId(UUID userId, Pageable pageable);

    boolean existsByPasswordIgnoreCase(String password);

    boolean existsByCompanyId(UUID id);

}
