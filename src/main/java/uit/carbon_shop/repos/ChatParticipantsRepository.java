package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.ChatParticipants;


public interface ChatParticipantsRepository extends JpaRepository<ChatParticipants, Long> {
}
