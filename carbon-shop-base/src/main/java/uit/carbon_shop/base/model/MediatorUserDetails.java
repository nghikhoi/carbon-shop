package uit.carbon_shop.base.model;

import java.util.Collection;
import java.util.UUID;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * Extension of Spring Security User class to store additional data.
 */
@Getter
public class MediatorUserDetails extends User {

    private final UUID userId;

    public MediatorUserDetails(final UUID userId, final String username, final String hash,
            final Collection<? extends GrantedAuthority> authorities) {
        super(username, hash, authorities);
        this.userId = userId;
    }

}
