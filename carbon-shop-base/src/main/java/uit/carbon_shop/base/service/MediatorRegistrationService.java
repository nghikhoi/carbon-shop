package uit.carbon_shop.base.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uit.carbon_shop.base.domain.Mediator;
import uit.carbon_shop.base.model.MediatorRegistrationRequest;
import uit.carbon_shop.base.repos.MediatorRepository;


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
        log.info("registering new user: {}", mediatorRegistrationRequest.getPassword());

        final Mediator mediator = new Mediator();
        mediator.setPassword(mediatorRegistrationRequest.getPassword());
        mediator.setPasswordSalt(passwordEncoder.encode(mediatorRegistrationRequest.getPassword()));
        mediator.setName(mediatorRegistrationRequest.getName());
        mediator.setPhone(mediatorRegistrationRequest.getPhone());
        mediator.setEmail(mediatorRegistrationRequest.getEmail());
        mediatorRepository.save(mediator);
    }

    public boolean passwordExists(final String password) {
        return mediatorRepository.existsByPasswordIgnoreCase(password);
    }

}
