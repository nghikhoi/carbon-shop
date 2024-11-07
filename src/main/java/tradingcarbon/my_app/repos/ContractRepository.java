package tradingcarbon.my_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import tradingcarbon.my_app.domain.Contract;


public interface ContractRepository extends JpaRepository<Contract, Long> {
}
