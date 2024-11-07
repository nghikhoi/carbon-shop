package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.Contract;


public interface ContractRepository extends JpaRepository<Contract, Long> {
}
