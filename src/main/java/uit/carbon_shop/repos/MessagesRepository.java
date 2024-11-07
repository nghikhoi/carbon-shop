package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.Messages;
import uit.carbon_shop.domain.User;


public interface MessagesRepository extends JpaRepository<Messages, Long> {

    Messages findFirstBySenderId(User user);

}
