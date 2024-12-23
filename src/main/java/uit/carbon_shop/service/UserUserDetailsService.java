package uit.carbon_shop.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.model.UserStatus;
import uit.carbon_shop.model.UserUserDetails;
import uit.carbon_shop.repos.AppUserRepository;


@Service
@Slf4j
public class UserUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public UserUserDetailsService(final AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserUserDetails loadUserByUsername(final String username) {
        final AppUser appUser = appUserRepository.findByEmailIgnoreCase(username);
        if (appUser == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        if (appUser.getStatus() != UserStatus.APPROVED) {
            log.warn("user not approved: {}", username);
            throw new UsernameNotFoundException("User " + username + " not approved");
        }
        final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(appUser.getRole().name()));
        return new UserUserDetails(appUser.getUserId(), username, appUser.getPassword(), authorities);
    }

}
