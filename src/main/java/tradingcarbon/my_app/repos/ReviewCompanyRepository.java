package tradingcarbon.my_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.ReviewCompany;


public interface ReviewCompanyRepository extends JpaRepository<ReviewCompany, Long> {
}
