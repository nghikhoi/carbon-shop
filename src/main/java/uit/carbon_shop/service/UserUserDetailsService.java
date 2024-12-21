package uit.carbon_shop.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.User;
import uit.carbon_shop.model.UserUserDetails;
import uit.carbon_shop.repos.UserRepository;


@Service
@Slf4j
public class UserUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserUserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByEmailIgnoreCase(username);
        if (user == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        return new UserUserDetails(user.getUserId(), username, user.getPassword(), authorities);
    }

}
