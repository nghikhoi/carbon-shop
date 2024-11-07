package tradingcarbon.my_app.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.Project;
import tradingcarbon.my_app.domain.ReviewProject;
import tradingcarbon.my_app.domain.User;


public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Project findFirstByUserId(User user);

    Project findFirstByReviewProjectId(ReviewProject reviewProject);

}
