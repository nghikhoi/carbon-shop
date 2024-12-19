package uit.carbon_shop.base.rest;

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
import uit.carbon_shop.base.model.ProjectDTO;
import uit.carbon_shop.base.service.ProjectService;
import uit.carbon_shop.base.util.ReferencedException;
import uit.carbon_shop.base.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectResource {

    private final ProjectService projectService;

    public ProjectResource(final ProjectService projectService) {
        this.projectService = projectService;
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
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "projectId") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(projectService.findAll(filter, pageable));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(
            @PathVariable(name = "projectId") final UUID projectId) {
        return ResponseEntity.ok(projectService.get(projectId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createProject(@RequestBody @Valid final ProjectDTO projectDTO) {
        final UUID createdProjectId = projectService.create(projectDTO);
        return new ResponseEntity<>(createdProjectId, HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<UUID> updateProject(
            @PathVariable(name = "projectId") final UUID projectId,
            @RequestBody @Valid final ProjectDTO projectDTO) {
        projectService.update(projectId, projectDTO);
        return ResponseEntity.ok(projectId);
    }

    @DeleteMapping("/{projectId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProject(
            @PathVariable(name = "projectId") final UUID projectId) {
        final ReferencedWarning referencedWarning = projectService.getReferencedWarning(projectId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        projectService.delete(projectId);
        return ResponseEntity.noContent().build();
    }

}
