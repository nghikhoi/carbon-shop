package uit.carbon_shop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.Mediator;
import uit.carbon_shop.model.MediatorRegistrationRequest;
import uit.carbon_shop.repos.MediatorRepository;


@Service
@Slf4j
public class MediatorRegistrationService {

    private final MediatorRepository mediatorRepository;
    private final PasswordEncoder passwordEncoder;

    public MediatorRegistrationService(final MediatorRepository mediatorRepository,
            final PasswordEncoder passwordEncoder) {
        this.mediatorRepository = mediatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(final MediatorRegistrationRequest mediatorRegistrationRequest) {
        log.info("registering new user: {}", mediatorRegistrationRequest.getEmail());

        final Mediator mediator = new Mediator();
        mediator.setPassword(passwordEncoder.encode(mediatorRegistrationRequest.getPassword()));
        mediator.setPasswordSalt(mediatorRegistrationRequest.getPasswordSalt());
        mediator.setName(mediatorRegistrationRequest.getName());
        mediator.setPhone(mediatorRegistrationRequest.getPhone());
        mediator.setEmail(mediatorRegistrationRequest.getEmail());
        mediatorRepository.save(mediator);
    }

    public boolean emailExists(final String email) {
        return mediatorRepository.existsByEmailIgnoreCase(email);
    }

}
