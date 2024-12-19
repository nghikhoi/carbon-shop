package uit.carbon_shop.base.rest;

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
import uit.carbon_shop.base.model.MediatorAuthenticationRequest;
import uit.carbon_shop.base.model.MediatorAuthenticationResponse;
import uit.carbon_shop.base.model.MediatorUserDetails;
import uit.carbon_shop.base.service.MediatorTokenService;
import uit.carbon_shop.base.service.MediatorUserDetailsService;


@RestController
public class MediatorAuthenticationResource {

    private final AuthenticationManager mediatorAuthenticationManager;
    private final MediatorUserDetailsService mediatorUserDetailsService;
    private final MediatorTokenService mediatorTokenService;

    public MediatorAuthenticationResource(
            @Qualifier("mediatorAuthenticationManager") final AuthenticationManager mediatorAuthenticationManager,
            final MediatorUserDetailsService mediatorUserDetailsService,
            final MediatorTokenService mediatorTokenService) {
        this.mediatorAuthenticationManager = mediatorAuthenticationManager;
        this.mediatorUserDetailsService = mediatorUserDetailsService;
        this.mediatorTokenService = mediatorTokenService;
    }

    @PostMapping("/mediator/authenticate")
    public MediatorAuthenticationResponse authenticate(
            @RequestBody @Valid final MediatorAuthenticationRequest authenticationRequest) {
        try {
            mediatorAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getPassword(), authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final MediatorUserDetails userDetails = mediatorUserDetailsService.loadUserByUsername(authenticationRequest.getPassword());
        final MediatorAuthenticationResponse authenticationResponse = new MediatorAuthenticationResponse();
        authenticationResponse.setAccessToken(mediatorTokenService.generateToken(userDetails));
        return authenticationResponse;
    }

}
