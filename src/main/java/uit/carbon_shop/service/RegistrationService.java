package uit.carbon_shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.model.AppUserDTO;
import uit.carbon_shop.model.CompanyDTO;
import uit.carbon_shop.model.CompanyStatus;
import uit.carbon_shop.model.RegistrationRequest;
import uit.carbon_shop.model.UserRegistrationRequest;
import uit.carbon_shop.model.UserRole;
import uit.carbon_shop.model.UserStatus;
import uit.carbon_shop.repos.AppUserRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyService companyService;
    private final AppUserService appUserService;
    private final IdGeneratorService idGeneratorService;

    public void register(final UserRegistrationRequest registrationRequest) {
        log.info("registering new user: {}", registrationRequest.getEmail());

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(idGeneratorService.generateId());
        companyDTO.setName(registrationRequest.getCompanyName());
        companyDTO.setAddress(registrationRequest.getCompanyAddress());
        companyDTO.setTaxCode(registrationRequest.getCompanyTaxCode());
        companyDTO.setEmail(registrationRequest.getCompanyEmail());
        companyDTO.setIndustry(registrationRequest.getCompanyIndustry());
        companyDTO.setStatus(CompanyStatus.APPROVED);
        companyService.create(companyDTO);

        final AppUserDTO appUser = new AppUserDTO();
        appUser.setUserId(idGeneratorService.generateId());
        appUser.setPassword(registrationRequest.getPassword());
        appUser.setName(registrationRequest.getName());
        appUser.setPhone(registrationRequest.getPhone());
        appUser.setEmail(registrationRequest.getEmail());
        appUser.setStatus(UserStatus.INIT);
        // assign default role
        appUser.setRole(UserRole.SELLER_OR_BUYER);
        appUser.setCompany(companyDTO.getId());
        appUserService.create(appUser);
    }

    public void registerMediator(final RegistrationRequest registrationRequest) {
        log.info("registering new mediator: {}", registrationRequest.getEmail());

        final AppUser appUser = new AppUser();
        appUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        appUser.setName(registrationRequest.getName());
        appUser.setPhone(registrationRequest.getPhone());
        appUser.setEmail(registrationRequest.getEmail());
        appUser.setStatus(UserStatus.APPROVED);
        // assign default role
        appUser.setRole(UserRole.MEDIATOR);
        appUserRepository.save(appUser);
    }

    public boolean emailExists(final String email) {
        return appUserRepository.existsByEmailIgnoreCase(email);
    }

}
