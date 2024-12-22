package uit.carbon_shop.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Question;


public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllById(Long id, Pageable pageable);

    Question findFirstByAskedBy(AppUser appUser);

}
