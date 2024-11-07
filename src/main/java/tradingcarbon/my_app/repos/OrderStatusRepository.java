package tradingcarbon.my_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.OrderStatus;
import tradingcarbon.my_app.domain.Project;


public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    OrderStatus findFirstByProjectId(Project project);

}
