package uit.carbon_shop.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Company;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.model.AppUserDTO;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.repos.ProjectRepository;
import uit.carbon_shop.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AppUserMapper {

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "favoriteProjects", ignore = true)
    @Mapping(target = "password", ignore = true)
    AppUserDTO updateAppUserDTO(AppUser appUser, @MappingTarget AppUserDTO appUserDTO);

    @AfterMapping
    default void afterUpdateAppUserDTO(AppUser appUser, @MappingTarget AppUserDTO appUserDTO) {
        appUserDTO.setCompany(appUser.getCompany() == null ? null : appUser.getCompany().getId());
        appUserDTO.setFavoriteProjects(appUser.getFavoriteProjects().stream()
                .map(project -> project.getProjectId())
                .toList());
    }

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "favoriteProjects", ignore = true)
    @Mapping(target = "password", ignore = true)
    AppUser updateAppUser(AppUserDTO appUserDTO, @MappingTarget AppUser appUser,
            @Context CompanyRepository companyRepository,
            @Context ProjectRepository projectRepository, @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    default void afterUpdateAppUser(AppUserDTO appUserDTO, @MappingTarget AppUser appUser,
            @Context CompanyRepository companyRepository,
            @Context ProjectRepository projectRepository,
            @Context PasswordEncoder passwordEncoder) {
        final Company company = appUserDTO.getCompany() == null ? null : companyRepository.findById(appUserDTO.getCompany())
                .orElseThrow(() -> new NotFoundException("company not found"));
        appUser.setCompany(company);
        final List<Project> favoriteProjects = projectRepository.findAllById(
                appUserDTO.getFavoriteProjects() == null ? Collections.emptyList() : appUserDTO.getFavoriteProjects());
        if (favoriteProjects.size() != (appUserDTO.getFavoriteProjects() == null ? 0 : appUserDTO.getFavoriteProjects().size())) {
            throw new NotFoundException("one of favoriteProjects not found");
        }
        appUser.setFavoriteProjects(new HashSet<>(favoriteProjects));
        appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
    }

}
