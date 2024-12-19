package uit.carbon_shop.service;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import uit.carbon_shop.domain.Company;
import uit.carbon_shop.domain.User;
import uit.carbon_shop.model.UserDTO;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserDTO updateUserDTO(User user, @MappingTarget UserDTO userDTO);

    @AfterMapping
    default void afterUpdateUserDTO(User user, @MappingTarget UserDTO userDTO) {
        userDTO.setCompany(user.getCompany() == null ? null : user.getCompany().getId());
    }

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "password", ignore = true)
    User updateUser(UserDTO userDTO, @MappingTarget User user,
            @Context CompanyRepository companyRepository, @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    default void afterUpdateUser(UserDTO userDTO, @MappingTarget User user,
            @Context CompanyRepository companyRepository,
            @Context PasswordEncoder passwordEncoder) {
        final Company company = userDTO.getCompany() == null ? null : companyRepository.findById(userDTO.getCompany())
                .orElseThrow(() -> new NotFoundException("company not found"));
        user.setCompany(company);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }

}
