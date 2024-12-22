package uit.carbon_shop.repos;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Company;
import uit.carbon_shop.domain.Project;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmailIgnoreCase(String email);

    AppUser findByResetPasswordUid(String resetPasswordUid);

    Page<AppUser> findAllByUserId(Long userId, Pageable pageable);

    boolean existsByEmailIgnoreCase(String email);

    AppUser findFirstByCompany(Company company);

    AppUser findFirstByFavoriteProjects(Project project);

    List<AppUser> findAllByFavoriteProjects(Project project);

    boolean existsByCompanyId(Long id);

}
