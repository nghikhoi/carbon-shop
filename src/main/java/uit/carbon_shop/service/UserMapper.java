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
import uit.carbon_shop.domain.Company;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.domain.User;
import uit.carbon_shop.model.UserDTO;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.repos.ProjectRepository;
import uit.carbon_shop.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "favoriteProjects", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserDTO updateUserDTO(User user, @MappingTarget UserDTO userDTO);

    @AfterMapping
    default void afterUpdateUserDTO(User user, @MappingTarget UserDTO userDTO) {
        userDTO.setCompany(user.getCompany() == null ? null : user.getCompany().getId());
        userDTO.setFavoriteProjects(user.getFavoriteProjects().stream()
                .map(project -> project.getProjectId())
                .toList());
    }

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "favoriteProjects", ignore = true)
    @Mapping(target = "password", ignore = true)
    User updateUser(UserDTO userDTO, @MappingTarget User user,
            @Context CompanyRepository companyRepository,
            @Context ProjectRepository projectRepository, @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    default void afterUpdateUser(UserDTO userDTO, @MappingTarget User user,
            @Context CompanyRepository companyRepository,
            @Context ProjectRepository projectRepository,
            @Context PasswordEncoder passwordEncoder) {
        final Company company = userDTO.getCompany() == null ? null : companyRepository.findById(userDTO.getCompany())
                .orElseThrow(() -> new NotFoundException("company not found"));
        user.setCompany(company);
        final List<Project> favoriteProjects = projectRepository.findAllById(
                userDTO.getFavoriteProjects() == null ? Collections.emptyList() : userDTO.getFavoriteProjects());
        if (favoriteProjects.size() != (userDTO.getFavoriteProjects() == null ? 0 : userDTO.getFavoriteProjects().size())) {
            throw new NotFoundException("one of favoriteProjects not found");
        }
        user.setFavoriteProjects(new HashSet<>(favoriteProjects));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }

}
