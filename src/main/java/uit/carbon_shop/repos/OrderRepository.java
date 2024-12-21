package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.domain.User;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findFirstByProject(Project project);

    Order findFirstByProcessBy(User user);

    Order findFirstByCreatedBy(User user);

}
