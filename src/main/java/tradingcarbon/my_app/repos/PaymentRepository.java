package tradingcarbon.my_app.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.Payment;


public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
