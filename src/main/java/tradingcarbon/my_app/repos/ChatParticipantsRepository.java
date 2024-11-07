package tradingcarbon.my_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.ChatParticipants;


public interface ChatParticipantsRepository extends JpaRepository<ChatParticipants, Long> {
}
