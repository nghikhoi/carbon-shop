package uit.carbon_shop.repos;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.domain.User;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmailIgnoreCase(String email);

    User findByResetPasswordUid(String resetPasswordUid);

    Page<User> findAllByUserId(UUID userId, Pageable pageable);

    boolean existsByEmailIgnoreCase(String email);

    User findFirstByFavoriteProjects(Project project);

    List<User> findAllByFavoriteProjects(Project project);

    boolean existsByCompanyId(UUID id);

}
