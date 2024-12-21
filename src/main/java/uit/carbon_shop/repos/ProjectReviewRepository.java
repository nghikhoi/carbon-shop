package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.domain.ProjectReview;


public interface ProjectReviewRepository extends JpaRepository<ProjectReview, Long> {

    ProjectReview findFirstByProject(Project project);

    ProjectReview findFirstByReviewBy(AppUser appUser);

}
