package uit.carbon_shop.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.Contract;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.domain.OrderStatus;
import uit.carbon_shop.domain.Payment;
import uit.carbon_shop.domain.Staff;
import uit.carbon_shop.domain.User;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findFirstByOrderStatusId(OrderStatus orderStatus);

    Order findFirstBySellerId(User user);

    Order findFirstByBuyerId(User user);

    Order findFirstByPaymentId(Payment payment);

    Order findFirstByConstractId(Contract contract);

    Order findFirstByStaffId(Staff staff);

    boolean existsByOrderStatusIdOrderStatusId(Long orderStatusId);

    boolean existsByPaymentIdPaymentId(UUID paymentId);

    boolean existsByConstractIdConstractId(Long constractId);

}
