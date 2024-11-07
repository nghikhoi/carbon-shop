package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.OrderStatus;
import uit.carbon_shop.domain.Project;


public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    OrderStatus findFirstByProjectId(Project project);

}
