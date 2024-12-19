package uit.carbon_shop.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uit.carbon_shop.model.UserAuthenticationRequest;
import uit.carbon_shop.model.UserAuthenticationResponse;
import uit.carbon_shop.model.UserUserDetails;
import uit.carbon_shop.service.UserTokenService;
import uit.carbon_shop.service.UserUserDetailsService;


@RestController
public class UserAuthenticationResource {

    private final AuthenticationManager userAuthenticationManager;
    private final UserUserDetailsService userUserDetailsService;
    private final UserTokenService userTokenService;

    public UserAuthenticationResource(
            @Qualifier("userAuthenticationManager") final AuthenticationManager userAuthenticationManager,
            final UserUserDetailsService userUserDetailsService,
            final UserTokenService userTokenService) {
        this.userAuthenticationManager = userAuthenticationManager;
        this.userUserDetailsService = userUserDetailsService;
        this.userTokenService = userTokenService;
    }

    @PostMapping("/user/authenticate")
    public UserAuthenticationResponse authenticate(
            @RequestBody @Valid final UserAuthenticationRequest authenticationRequest) {
        try {
            userAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserUserDetails userDetails = userUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final UserAuthenticationResponse authenticationResponse = new UserAuthenticationResponse();
        authenticationResponse.setAccessToken(userTokenService.generateToken(userDetails));
        return authenticationResponse;
    }

}
