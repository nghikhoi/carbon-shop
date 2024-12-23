package uit.carbon_shop.service;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.domain.ProjectReview;
import uit.carbon_shop.model.ProjectReviewDTO;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.repos.ProjectRepository;
import uit.carbon_shop.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectReviewMapper {

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "reviewBy", ignore = true)
    ProjectReviewDTO updateProjectReviewDTO(ProjectReview projectReview,
            @MappingTarget ProjectReviewDTO projectReviewDTO);

    @AfterMapping
    default void afterUpdateProjectReviewDTO(ProjectReview projectReview,
            @MappingTarget ProjectReviewDTO projectReviewDTO) {
        projectReviewDTO.setProject(projectReview.getProject() == null ? null : projectReview.getProject().getProjectId());
        projectReviewDTO.setReviewBy(projectReview.getReviewBy() == null ? null : projectReview.getReviewBy().getUserId());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "reviewBy", ignore = true)
    ProjectReview updateProjectReview(ProjectReviewDTO projectReviewDTO,
            @MappingTarget ProjectReview projectReview,
            @Context ProjectRepository projectRepository,
            @Context AppUserRepository appUserRepository);

    @AfterMapping
    default void afterUpdateProjectReview(ProjectReviewDTO projectReviewDTO,
            @MappingTarget ProjectReview projectReview,
            @Context ProjectRepository projectRepository,
            @Context AppUserRepository appUserRepository) {
        final Project project = projectReviewDTO.getProject() == null ? null : projectRepository.findById(projectReviewDTO.getProject())
                .orElseThrow(() -> new NotFoundException("project not found"));
        projectReview.setProject(project);
        final AppUser reviewBy = projectReviewDTO.getReviewBy() == null ? null : appUserRepository.findById(projectReviewDTO.getReviewBy())
                .orElseThrow(() -> new NotFoundException("reviewBy not found"));
        projectReview.setReviewBy(reviewBy);
    }

}
