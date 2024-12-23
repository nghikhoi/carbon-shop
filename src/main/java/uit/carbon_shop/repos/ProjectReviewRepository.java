package uit.carbon_shop.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.domain.ProjectReview;


public interface ProjectReviewRepository extends JpaRepository<ProjectReview, Long> {

    Page<ProjectReview> findAllById(Long id, Pageable pageable);

    ProjectReview findFirstByProject(Project project);

    ProjectReview findFirstByReviewBy(AppUser appUser);

}
