package uit.carbon_shop.base.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.base.domain.Order;
import uit.carbon_shop.base.domain.Project;
import uit.carbon_shop.base.domain.User;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findFirstByProject(Project project);

    Order findFirstByCreatedBy(User user);

}
