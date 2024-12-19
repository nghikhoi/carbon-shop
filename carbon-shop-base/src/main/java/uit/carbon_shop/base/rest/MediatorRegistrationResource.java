package uit.carbon_shop.base.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.base.model.MediatorRegistrationRequest;
import uit.carbon_shop.base.service.MediatorRegistrationService;


@RestController
public class MediatorRegistrationResource {

    private final MediatorRegistrationService mediatorRegistrationService;

    public MediatorRegistrationResource(
            final MediatorRegistrationService mediatorRegistrationService) {
        this.mediatorRegistrationService = mediatorRegistrationService;
    }

    @PostMapping("/mediator/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid final MediatorRegistrationRequest mediatorRegistrationRequest) {
        mediatorRegistrationService.register(mediatorRegistrationRequest);
        return ResponseEntity.ok().build();
    }

}
