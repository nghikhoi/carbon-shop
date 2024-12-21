package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Company;
import uit.carbon_shop.domain.CompanyReview;


public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Long> {

    CompanyReview findFirstByCompany(Company company);

    CompanyReview findFirstByReviewBy(AppUser appUser);

}
