package uit.carbon_shop.base.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uit.carbon_shop.base.domain.User;
import uit.carbon_shop.base.model.UserRegistrationRequest;
import uit.carbon_shop.base.repos.UserRepository;


@Service
@Slf4j
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(final UserRepository userRepository,
            final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(final UserRegistrationRequest userRegistrationRequest) {
        log.info("registering new user: {}", userRegistrationRequest.getPassword());

        final User user = new User();
        user.setPassword(userRegistrationRequest.getPassword());
        user.setPasswordSalt(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        user.setName(userRegistrationRequest.getName());
        user.setPhone(userRegistrationRequest.getPhone());
        user.setEmail(userRegistrationRequest.getEmail());
        userRepository.save(user);
    }

    public boolean passwordExists(final String password) {
        return userRepository.existsByPasswordIgnoreCase(password);
    }

}
