package tradingcarbon.my_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.Messages;
import tradingcarbon.my_app.domain.User;


public interface MessagesRepository extends JpaRepository<Messages, Long> {

    Messages findFirstBySenderId(User user);

}
