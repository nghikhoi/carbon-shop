package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.CompanyReview;
import uit.carbon_shop.domain.User;


public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Long> {

    CompanyReview findFirstByReviewBy(User user);

}
