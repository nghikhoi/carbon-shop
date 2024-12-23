package uit.carbon_shop.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.domain.Project;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByOrderId(Long orderId, Pageable pageable);

    Page<Order> findByCreatedBy_UserId(Long userId, Pageable pageable);

    @Query("select o from Order o where o.project.ownerCompany.id = ?1")
    Page<Order> findByProject_OwnerCompany_Id(Long id, Pageable pageable);

    Order findFirstByProject(Project project);

    Order findFirstByProcessBy(AppUser appUser);

    Order findFirstByCreatedBy(AppUser appUser);

}
