package uit.carbon_shop.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.UserRegistrationRequest;
import uit.carbon_shop.service.UserRegistrationService;


@RestController
public class UserRegistrationResource {

    private final UserRegistrationService userRegistrationService;

    public UserRegistrationResource(final UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid final UserRegistrationRequest userRegistrationRequest) {
        userRegistrationService.register(userRegistrationRequest);
        return ResponseEntity.ok().build();
    }

}
