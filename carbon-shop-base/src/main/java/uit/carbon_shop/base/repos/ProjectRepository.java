package uit.carbon_shop.base.repos;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.base.domain.Project;


public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Page<Project> findAllByProjectId(UUID projectId, Pageable pageable);

}
