package uit.carbon_shop.repos;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Company;
import uit.carbon_shop.domain.Project;


public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Page<Project> findAllByProjectId(UUID projectId, Pageable pageable);

    Project findFirstByOwnerCompany(Company company);

    Project findFirstByAuditBy(AppUser appUser);

}
