package uit.carbon_shop.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.Payment;


public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
