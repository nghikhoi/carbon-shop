package uit.carbon_shop.service;

import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.domain.ProjectReview;
import uit.carbon_shop.model.ProjectDTO;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.repos.OrderRepository;
import uit.carbon_shop.repos.ProjectRepository;
import uit.carbon_shop.repos.ProjectReviewRepository;
import uit.carbon_shop.util.NotFoundException;
import uit.carbon_shop.util.ReferencedWarning;


@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final AppUserRepository appUserRepository;
    private final ProjectMapper projectMapper;
    private final OrderRepository orderRepository;
    private final ProjectReviewRepository projectReviewRepository;

    public ProjectService(final ProjectRepository projectRepository,
            final CompanyRepository companyRepository, final AppUserRepository appUserRepository,
            final ProjectMapper projectMapper, final OrderRepository orderRepository,
            final ProjectReviewRepository projectReviewRepository) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.appUserRepository = appUserRepository;
        this.projectMapper = projectMapper;
        this.orderRepository = orderRepository;
        this.projectReviewRepository = projectReviewRepository;
    }

    public Page<ProjectDTO> findAll(final String filter, final Pageable pageable) {
        Page<Project> page;
        if (filter != null) {
            UUID uuidFilter = null;
            try {
                uuidFilter = UUID.fromString(filter);
            } catch (final IllegalArgumentException illegalArgumentException) {
                // keep null - no parseable input
            }
            page = projectRepository.findAllByProjectId(uuidFilter, pageable);
        } else {
            page = projectRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(project -> projectMapper.updateProjectDTO(project, new ProjectDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    public ProjectDTO get(final UUID projectId) {
        return projectRepository.findById(projectId)
                .map(project -> projectMapper.updateProjectDTO(project, new ProjectDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ProjectDTO projectDTO) {
        final Project project = new Project();
        projectMapper.updateProject(projectDTO, project, companyRepository, appUserRepository);
        return projectRepository.save(project).getProjectId();
    }

    public void update(final UUID projectId, final ProjectDTO projectDTO) {
        final Project project = projectRepository.findById(projectId)
                .orElseThrow(NotFoundException::new);
        projectMapper.updateProject(projectDTO, project, companyRepository, appUserRepository);
        projectRepository.save(project);
    }

    public void delete(final UUID projectId) {
        final Project project = projectRepository.findById(projectId)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        appUserRepository.findAllByFavoriteProjects(project)
                .forEach(appUser -> appUser.getFavoriteProjects().remove(project));
        projectRepository.delete(project);
    }

    public ReferencedWarning getReferencedWarning(final UUID projectId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Project project = projectRepository.findById(projectId)
                .orElseThrow(NotFoundException::new);
        final Order projectOrder = orderRepository.findFirstByProject(project);
        if (projectOrder != null) {
            referencedWarning.setKey("project.order.project.referenced");
            referencedWarning.addParam(projectOrder.getOrderId());
            return referencedWarning;
        }
        final ProjectReview projectProjectReview = projectReviewRepository.findFirstByProject(project);
        if (projectProjectReview != null) {
            referencedWarning.setKey("project.projectReview.project.referenced");
            referencedWarning.addParam(projectProjectReview.getId());
            return referencedWarning;
        }
        return null;
    }

}
