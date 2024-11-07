package uit.carbon_shop.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.domain.ReviewProject;
import uit.carbon_shop.domain.User;


public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Project findFirstByUserId(User user);

    Project findFirstByReviewProjectId(ReviewProject reviewProject);

}
