package uit.carbon_shop.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uit.carbon_shop.model.AuthenticationRequest;
import uit.carbon_shop.model.AuthenticationResponse;
import uit.carbon_shop.model.UserUserDetails;
import uit.carbon_shop.service.AppUserService;
import uit.carbon_shop.service.UserTokenService;
import uit.carbon_shop.service.UserUserDetailsService;


@RestController
@RequiredArgsConstructor
public class AuthenticationResource {

    private final AuthenticationManager authenticationManager;
    private final UserUserDetailsService userUserDetailsService;
    private final UserTokenService userTokenService;
    private final AppUserService appUserService;

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(
            @RequestBody @Valid final AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserUserDetails userDetails = userUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(userTokenService.generateToken(userDetails));
        authenticationResponse.setUser(appUserService.get(userDetails.getUserId()));
        return authenticationResponse;
    }

}
