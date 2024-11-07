package uit.carbon_shop.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.Chats;
import uit.carbon_shop.domain.User;


public interface ChatsRepository extends JpaRepository<Chats, Long> {

    Chats findFirstByUserId(User user);

    List<Chats> findAllByUserId(User user);

}
