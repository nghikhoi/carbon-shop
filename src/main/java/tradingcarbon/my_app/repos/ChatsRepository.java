package tradingcarbon.my_app.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.Chats;
import tradingcarbon.my_app.domain.User;


public interface ChatsRepository extends JpaRepository<Chats, Long> {

    Chats findFirstByUserId(User user);

    List<Chats> findAllByUserId(User user);

}
