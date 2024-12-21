package uit.carbon_shop.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.UserAskDTO;
import uit.carbon_shop.util.UserRoles;


@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('" + UserRoles.BUYER + "', '" + UserRoles.SELLER + "')")
@SecurityRequirement(name = "bearer-jwt")
public class UserController {

    @PostMapping("/question")
    public ResponseEntity<Void> newQuestion(@RequestBody @Valid final UserAskDTO userAskDTO) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/questions")
    public ResponseEntity<Void> viewQuestions() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/projects")
    public ResponseEntity<Void> viewAllProject() {
        return ResponseEntity.ok().build();
    }

}
