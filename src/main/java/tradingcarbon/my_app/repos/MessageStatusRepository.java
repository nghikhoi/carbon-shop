package tradingcarbon.my_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.MessageStatus;


public interface MessageStatusRepository extends JpaRepository<MessageStatus, Long> {
}
