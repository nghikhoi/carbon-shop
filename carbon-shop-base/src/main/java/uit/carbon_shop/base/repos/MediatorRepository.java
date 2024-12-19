package uit.carbon_shop.base.repos;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.base.domain.Mediator;


public interface MediatorRepository extends JpaRepository<Mediator, UUID> {

    Mediator findByPasswordIgnoreCase(String password);

    Mediator findByResetPasswordUid(String resetPasswordUid);

    boolean existsByPasswordIgnoreCase(String password);

}
