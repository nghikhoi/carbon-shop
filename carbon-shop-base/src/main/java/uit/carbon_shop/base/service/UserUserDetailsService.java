package uit.carbon_shop.base.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uit.carbon_shop.base.domain.User;
import uit.carbon_shop.base.model.UserUserDetails;
import uit.carbon_shop.base.repos.UserRepository;
import uit.carbon_shop.base.util.UserRoles;


@Service
@Slf4j
public class UserUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserUserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByPasswordIgnoreCase(username);
        if (user == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        final String role = "seller@invalid.bootify.io".equals(username) ? UserRoles.SELLER : UserRoles.BUYER;
        final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        return new UserUserDetails(user.getUserId(), username, user.getPasswordSalt(), authorities);
    }

}
