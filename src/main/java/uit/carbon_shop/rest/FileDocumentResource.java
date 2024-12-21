package uit.carbon_shop.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.FileDocumentDTO;
import uit.carbon_shop.model.UserRole;
import uit.carbon_shop.service.FileDocumentService;


@RestController
@RequestMapping(value = "/api/fileDocuments", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('" + UserRole.Fields.SELLER_OR_BUYER + "', '" + UserRole.Fields.MEDIATOR + "')")
@SecurityRequirement(name = "bearer-jwt")
public class FileDocumentResource {

    private final FileDocumentService fileDocumentService;

    public FileDocumentResource(final FileDocumentService fileDocumentService) {
        this.fileDocumentService = fileDocumentService;
    }

    @GetMapping
    public ResponseEntity<List<FileDocumentDTO>> getAllFileDocuments() {
        return ResponseEntity.ok(fileDocumentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDocumentDTO> getFileDocument(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(fileDocumentService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFileDocument(
            @RequestBody @Valid final FileDocumentDTO fileDocumentDTO) {
        final Long createdId = fileDocumentService.create(fileDocumentDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFileDocument(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FileDocumentDTO fileDocumentDTO) {
        fileDocumentService.update(id, fileDocumentDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFileDocument(@PathVariable(name = "id") final Long id) {
        fileDocumentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
