package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.MessageStatus;


public interface MessageStatusRepository extends JpaRepository<MessageStatus, Long> {
}
