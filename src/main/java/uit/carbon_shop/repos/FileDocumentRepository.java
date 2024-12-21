package uit.carbon_shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.carbon_shop.domain.FileDocument;


public interface FileDocumentRepository extends JpaRepository<FileDocument, Long> {
}
