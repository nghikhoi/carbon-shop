package uit.carbon_shop.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.AppUserDTO;
import uit.carbon_shop.service.AppUserService;
import uit.carbon_shop.util.ReferencedException;
import uit.carbon_shop.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/appUsers", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserResource {

    private final AppUserService appUserService;

    public AppUserResource(final AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Operation(
            parameters = {
                    @Parameter(
                            name = "page",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "size",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "sort",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = String.class)
                    )
            }
    )
    @GetMapping
    public ResponseEntity<Page<AppUserDTO>> getAllAppUsers(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "userId") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(appUserService.findAll(filter, pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AppUserDTO> getAppUser(@PathVariable(name = "userId") final UUID userId) {
        return ResponseEntity.ok(appUserService.get(userId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createAppUser(@RequestBody @Valid final AppUserDTO appUserDTO) {
        final UUID createdUserId = appUserService.create(appUserDTO);
        return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UUID> updateAppUser(@PathVariable(name = "userId") final UUID userId,
            @RequestBody @Valid final AppUserDTO appUserDTO) {
        appUserService.update(userId, appUserDTO);
        return ResponseEntity.ok(userId);
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAppUser(@PathVariable(name = "userId") final UUID userId) {
        final ReferencedWarning referencedWarning = appUserService.getReferencedWarning(userId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        appUserService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
