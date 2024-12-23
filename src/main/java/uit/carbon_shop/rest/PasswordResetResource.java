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
import uit.carbon_shop.model.PasswordResetCompleteRequest;
import uit.carbon_shop.model.PasswordResetRequest;
import uit.carbon_shop.service.PasswordResetService;


@RestController
@RequestMapping(value = "/passwordReset", produces = MediaType.APPLICATION_JSON_VALUE)
public class PasswordResetResource {

    private final PasswordResetService passwordResetService;

    public PasswordResetResource(final PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/start")
    public ResponseEntity<Void> start(
            @RequestBody @Valid final PasswordResetRequest passwordResetRequest) {
        passwordResetService.startProcess(passwordResetRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/isValidUid")
    public ResponseEntity<Boolean> isValidUid(@RequestParam("uid") String passwordResetUid) {
        return ResponseEntity.ok(passwordResetService.isValidPasswordResetUid(passwordResetUid));
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(
            @RequestBody @Valid final PasswordResetCompleteRequest passwordResetCompleteRequest) {
        passwordResetService.completeProcess(passwordResetCompleteRequest);
        return ResponseEntity.ok().build();
    }

}
