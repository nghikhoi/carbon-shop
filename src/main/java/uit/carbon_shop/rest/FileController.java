package uit.carbon_shop.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.util.UserRoles;


@RestController
@RequestMapping(value = "/api/file", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('" + UserRoles.BUYER + "', '" + UserRoles.SELLER + "')")
@SecurityRequirement(name = "bearer-jwt")
public class FileController {

    @PostMapping("/upload")
    public ResponseEntity<Void> upload() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Void> get(@PathVariable(name = "fileId") final String fileId) {
        return ResponseEntity.ok().build();
    }

}
