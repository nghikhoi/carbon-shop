package uit.carbon_shop.rest;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.MediatorPasswordResetCompleteRequest;
import uit.carbon_shop.model.MediatorPasswordResetRequest;
import uit.carbon_shop.service.MediatorPasswordResetService;


@RestController
@RequestMapping(value = "/mediator/passwordReset", produces = MediaType.APPLICATION_JSON_VALUE)
public class MediatorPasswordResetResource {

    private final MediatorPasswordResetService mediatorPasswordResetService;

    public MediatorPasswordResetResource(
            final MediatorPasswordResetService mediatorPasswordResetService) {
        this.mediatorPasswordResetService = mediatorPasswordResetService;
    }

    @PostMapping("/start")
    public ResponseEntity<Void> start(
            @RequestBody @Valid final MediatorPasswordResetRequest passwordResetRequest) {
        mediatorPasswordResetService.startProcess(passwordResetRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/isValidUid")
    public ResponseEntity<Boolean> isValidUid(@RequestParam("uid") String passwordResetUid) {
        return ResponseEntity.ok(mediatorPasswordResetService.isValidPasswordResetUid(passwordResetUid));
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(
            @RequestBody @Valid final MediatorPasswordResetCompleteRequest passwordResetCompleteRequest) {
        mediatorPasswordResetService.completeProcess(passwordResetCompleteRequest);
        return ResponseEntity.ok().build();
    }

}
