package uit.carbon_shop.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.Mediator;
import uit.carbon_shop.model.MediatorUserDetails;
import uit.carbon_shop.repos.MediatorRepository;
import uit.carbon_shop.util.UserRoles;


@Service
@Slf4j
public class MediatorUserDetailsService implements UserDetailsService {

    private final MediatorRepository mediatorRepository;

    public MediatorUserDetailsService(final MediatorRepository mediatorRepository) {
        this.mediatorRepository = mediatorRepository;
    }

    @Override
    public MediatorUserDetails loadUserByUsername(final String username) {
        final Mediator mediator = mediatorRepository.findByEmailIgnoreCase(username);
        if (mediator == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        final String role = UserRoles.MEDIATOR;
        final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        return new MediatorUserDetails(mediator.getUserId(), username, mediator.getPassword(), authorities);
    }

}
