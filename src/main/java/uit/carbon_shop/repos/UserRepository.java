package uit.carbon_shop.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.ReviewCompany;
import uit.carbon_shop.domain.User;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findFirstByReviewCompanyId(ReviewCompany reviewCompany);

}
