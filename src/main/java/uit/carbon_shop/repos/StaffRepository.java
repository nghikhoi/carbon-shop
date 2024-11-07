package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.Staff;


public interface StaffRepository extends JpaRepository<Staff, Long> {
}
