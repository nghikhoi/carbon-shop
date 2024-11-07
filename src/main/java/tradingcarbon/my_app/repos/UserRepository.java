package tradingcarbon.my_app.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.ReviewCompany;
import tradingcarbon.my_app.domain.User;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findFirstByReviewCompanyId(ReviewCompany reviewCompany);

}
