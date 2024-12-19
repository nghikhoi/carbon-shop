package uit.carbon_shop.service;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uit.carbon_shop.domain.Company;
import uit.carbon_shop.domain.Mediator;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.model.ProjectDTO;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.repos.MediatorRepository;
import uit.carbon_shop.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectMapper {

    @Mapping(target = "ownerCompany", ignore = true)
    @Mapping(target = "auditBy", ignore = true)
    ProjectDTO updateProjectDTO(Project project, @MappingTarget ProjectDTO projectDTO);

    @AfterMapping
    default void afterUpdateProjectDTO(Project project, @MappingTarget ProjectDTO projectDTO) {
        projectDTO.setOwnerCompany(project.getOwnerCompany() == null ? null : project.getOwnerCompany().getId());
        projectDTO.setAuditBy(project.getAuditBy() == null ? null : project.getAuditBy().getUserId());
    }

    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "ownerCompany", ignore = true)
    @Mapping(target = "auditBy", ignore = true)
    Project updateProject(ProjectDTO projectDTO, @MappingTarget Project project,
            @Context CompanyRepository companyRepository,
            @Context MediatorRepository mediatorRepository);

    @AfterMapping
    default void afterUpdateProject(ProjectDTO projectDTO, @MappingTarget Project project,
            @Context CompanyRepository companyRepository,
            @Context MediatorRepository mediatorRepository) {
        final Company ownerCompany = projectDTO.getOwnerCompany() == null ? null : companyRepository.findById(projectDTO.getOwnerCompany())
                .orElseThrow(() -> new NotFoundException("ownerCompany not found"));
        project.setOwnerCompany(ownerCompany);
        final Mediator auditBy = projectDTO.getAuditBy() == null ? null : mediatorRepository.findById(projectDTO.getAuditBy())
                .orElseThrow(() -> new NotFoundException("auditBy not found"));
        project.setAuditBy(auditBy);
    }

}
