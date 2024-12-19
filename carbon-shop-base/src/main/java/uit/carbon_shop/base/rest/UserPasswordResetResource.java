package uit.carbon_shop.base.rest;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.base.model.UserPasswordResetCompleteRequest;
import uit.carbon_shop.base.model.UserPasswordResetRequest;
import uit.carbon_shop.base.service.UserPasswordResetService;


@RestController
@RequestMapping(value = "/user/passwordReset", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserPasswordResetResource {

    private final UserPasswordResetService userPasswordResetService;

    public UserPasswordResetResource(final UserPasswordResetService userPasswordResetService) {
        this.userPasswordResetService = userPasswordResetService;
    }

    @PostMapping("/start")
    public ResponseEntity<Void> start(
            @RequestBody @Valid final UserPasswordResetRequest passwordResetRequest) {
        userPasswordResetService.startProcess(passwordResetRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/isValidUid")
    public ResponseEntity<Boolean> isValidUid(@RequestParam("uid") String passwordResetUid) {
        return ResponseEntity.ok(userPasswordResetService.isValidPasswordResetUid(passwordResetUid));
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(
            @RequestBody @Valid final UserPasswordResetCompleteRequest passwordResetCompleteRequest) {
        userPasswordResetService.completeProcess(passwordResetCompleteRequest);
        return ResponseEntity.ok().build();
    }

}
