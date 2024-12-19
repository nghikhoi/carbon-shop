package uit.carbon_shop.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.Mediator;


public interface MediatorRepository extends JpaRepository<Mediator, UUID> {

    Mediator findByEmailIgnoreCase(String email);

    Mediator findByResetPasswordUid(String resetPasswordUid);

    boolean existsByEmailIgnoreCase(String email);

}
