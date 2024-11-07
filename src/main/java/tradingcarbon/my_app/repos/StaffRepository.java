package tradingcarbon.my_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.Staff;


public interface StaffRepository extends JpaRepository<Staff, Long> {
}
