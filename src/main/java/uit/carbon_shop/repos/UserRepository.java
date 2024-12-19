package uit.carbon_shop.repos;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.User;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmailIgnoreCase(String email);

    User findByResetPasswordUid(String resetPasswordUid);

    Page<User> findAllByUserId(UUID userId, Pageable pageable);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByCompanyId(UUID id);

}
