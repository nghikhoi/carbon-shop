package uit.carbon_shop.base.service;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import uit.carbon_shop.base.domain.Company;
import uit.carbon_shop.base.domain.User;
import uit.carbon_shop.base.model.UserDTO;
import uit.carbon_shop.base.repos.CompanyRepository;
import uit.carbon_shop.base.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "passwordSalt", ignore = true)
    UserDTO updateUserDTO(User user, @MappingTarget UserDTO userDTO);

    @AfterMapping
    default void afterUpdateUserDTO(User user, @MappingTarget UserDTO userDTO) {
        userDTO.setCompany(user.getCompany() == null ? null : user.getCompany().getId());
    }

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "passwordSalt", ignore = true)
    User updateUser(UserDTO userDTO, @MappingTarget User user,
            @Context CompanyRepository companyRepository, @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    default void afterUpdateUser(UserDTO userDTO, @MappingTarget User user,
            @Context CompanyRepository companyRepository,
            @Context PasswordEncoder passwordEncoder) {
        final Company company = userDTO.getCompany() == null ? null : companyRepository.findById(userDTO.getCompany())
                .orElseThrow(() -> new NotFoundException("company not found"));
        user.setCompany(company);
        user.setPasswordSalt(passwordEncoder.encode(userDTO.getPasswordSalt()));
    }

}
