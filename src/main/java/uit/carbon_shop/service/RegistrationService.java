package uit.carbon_shop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.model.RegistrationRequest;
import uit.carbon_shop.model.UserRole;
import uit.carbon_shop.model.UserStatus;
import uit.carbon_shop.repos.AppUserRepository;


@Service
@Slf4j
public class RegistrationService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(final AppUserRepository appUserRepository,
            final PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(final RegistrationRequest registrationRequest) {
        log.info("registering new user: {}", registrationRequest.getEmail());

        final AppUser appUser = new AppUser();
        appUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        appUser.setName(registrationRequest.getName());
        appUser.setPhone(registrationRequest.getPhone());
        appUser.setEmail(registrationRequest.getEmail());
        appUser.setStatus(UserStatus.INIT);
        // assign default role
        appUser.setRole(UserRole.SELLER_OR_BUYER);
        appUserRepository.save(appUser);
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
