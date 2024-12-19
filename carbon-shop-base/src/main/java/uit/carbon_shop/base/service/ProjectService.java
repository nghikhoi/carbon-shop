package uit.carbon_shop.base.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uit.carbon_shop.base.model.ProjectDTO;
import uit.carbon_shop.base.util.ReferencedWarning;


public interface ProjectService {

    Page<ProjectDTO> findAll(String filter, Pageable pageable);

    ProjectDTO get(UUID projectId);

    UUID create(ProjectDTO projectDTO);

    void update(UUID projectId, ProjectDTO projectDTO);

    void delete(UUID projectId);

    ReferencedWarning getReferencedWarning(UUID projectId);

}
