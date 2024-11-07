package tradingcarbon.my_app.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.Contract;
import tradingcarbon.my_app.domain.Order;
import tradingcarbon.my_app.domain.OrderStatus;
import tradingcarbon.my_app.domain.Payment;
import tradingcarbon.my_app.domain.Staff;
import tradingcarbon.my_app.domain.User;


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
