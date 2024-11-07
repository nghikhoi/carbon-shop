package tradingcarbon.my_app.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tradingcarbon.my_app.domain.OrderStatus;
import tradingcarbon.my_app.domain.Project;
import tradingcarbon.my_app.domain.ReviewProject;
import tradingcarbon.my_app.domain.User;
import tradingcarbon.my_app.model.ProjectDTO;
import tradingcarbon.my_app.repos.OrderStatusRepository;
import tradingcarbon.my_app.repos.ProjectRepository;
import tradingcarbon.my_app.repos.ReviewProjectRepository;
import tradingcarbon.my_app.repos.UserRepository;
import tradingcarbon.my_app.util.NotFoundException;
import tradingcarbon.my_app.util.ReferencedWarning;


@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ReviewProjectRepository reviewProjectRepository;
    private final OrderStatusRepository orderStatusRepository;

    public ProjectService(final ProjectRepository projectRepository,
            final UserRepository userRepository,
            final ReviewProjectRepository reviewProjectRepository,
            final OrderStatusRepository orderStatusRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.reviewProjectRepository = reviewProjectRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    public List<ProjectDTO> findAll() {
        final List<Project> projects = projectRepository.findAll(Sort.by("projectId"));
        return projects.stream()
                .map(project -> mapToDTO(project, new ProjectDTO()))
                .toList();
    }

    public ProjectDTO get(final UUID projectId) {
        return projectRepository.findById(projectId)
                .map(project -> mapToDTO(project, new ProjectDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ProjectDTO projectDTO) {
        final Project project = new Project();
        mapToEntity(projectDTO, project);
        return projectRepository.save(project).getProjectId();
    }

    public void update(final UUID projectId, final ProjectDTO projectDTO) {
        final Project project = projectRepository.findById(projectId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(projectDTO, project);
        projectRepository.save(project);
    }

    public void delete(final UUID projectId) {
        projectRepository.deleteById(projectId);
    }

    private ProjectDTO mapToDTO(final Project project, final ProjectDTO projectDTO) {
        projectDTO.setProjectId(project.getProjectId());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setProjectAddress(project.getProjectAddress());
        projectDTO.setProjectSize(project.getProjectSize());
        projectDTO.setProjectTimeStart(project.getProjectTimeStart());
        projectDTO.setProjectTimeEnd(project.getProjectTimeEnd());
        projectDTO.setProjectRangeCarbon(project.getProjectRangeCarbon());
        projectDTO.setOrganizationProvide(project.getOrganizationProvide());
        projectDTO.setNumberCarBonCredit(project.getNumberCarBonCredit());
        projectDTO.setCreditTimeStart(project.getCreditTimeStart());
        projectDTO.setPrice(project.getPrice());
        projectDTO.setMethodPayment(project.getMethodPayment());
        projectDTO.setProjectCredit(project.getProjectCredit());
        projectDTO.setCreditDetail(project.getCreditDetail());
        projectDTO.setCreditId(project.getCreditId());
        projectDTO.setImage(project.getImage());
        projectDTO.setUserId(project.getUserId() == null ? null : project.getUserId().getUserId());
        projectDTO.setReviewProjectId(project.getReviewProjectId() == null ? null : project.getReviewProjectId().getReviewProjectId());
        return projectDTO;
    }

    private Project mapToEntity(final ProjectDTO projectDTO, final Project project) {
        project.setProjectName(projectDTO.getProjectName());
        project.setProjectAddress(projectDTO.getProjectAddress());
        project.setProjectSize(projectDTO.getProjectSize());
        project.setProjectTimeStart(projectDTO.getProjectTimeStart());
        project.setProjectTimeEnd(projectDTO.getProjectTimeEnd());
        project.setProjectRangeCarbon(projectDTO.getProjectRangeCarbon());
        project.setOrganizationProvide(projectDTO.getOrganizationProvide());
        project.setNumberCarBonCredit(projectDTO.getNumberCarBonCredit());
        project.setCreditTimeStart(projectDTO.getCreditTimeStart());
        project.setPrice(projectDTO.getPrice());
        project.setMethodPayment(projectDTO.getMethodPayment());
        project.setProjectCredit(projectDTO.getProjectCredit());
        project.setCreditDetail(projectDTO.getCreditDetail());
        project.setCreditId(projectDTO.getCreditId());
        project.setImage(projectDTO.getImage());
        final User userId = projectDTO.getUserId() == null ? null : userRepository.findById(projectDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("userId not found"));
        project.setUserId(userId);
        final ReviewProject reviewProjectId = projectDTO.getReviewProjectId() == null ? null : reviewProjectRepository.findById(projectDTO.getReviewProjectId())
                .orElseThrow(() -> new NotFoundException("reviewProjectId not found"));
        project.setReviewProjectId(reviewProjectId);
        return project;
    }

    public ReferencedWarning getReferencedWarning(final UUID projectId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Project project = projectRepository.findById(projectId)
                .orElseThrow(NotFoundException::new);
        final OrderStatus projectIdOrderStatus = orderStatusRepository.findFirstByProjectId(project);
        if (projectIdOrderStatus != null) {
            referencedWarning.setKey("project.orderStatus.projectId.referenced");
            referencedWarning.addParam(projectIdOrderStatus.getOrderStatusId());
            return referencedWarning;
        }
        return null;
    }

}
