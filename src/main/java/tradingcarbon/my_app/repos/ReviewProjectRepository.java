package tradingcarbon.my_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.ReviewProject;


public interface ReviewProjectRepository extends JpaRepository<ReviewProject, Long> {
}
