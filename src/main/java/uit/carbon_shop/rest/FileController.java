package uit.carbon_shop.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uit.carbon_shop.domain.FileDocument;
import uit.carbon_shop.model.UserRole;
import uit.carbon_shop.repos.FileContentStore;
import uit.carbon_shop.repos.FileDocumentRepository;
import uit.carbon_shop.service.IdGeneratorService;


@RestController
@RequestMapping(value = "/api/file", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileDocumentRepository fileRepository;
    private final FileContentStore fileContentStore;
    private final IdGeneratorService idGeneratorService;

    @PostMapping("/upload")
    @SneakyThrows
    @PreAuthorize("hasAnyAuthority('" + UserRole.Fields.SELLER_OR_BUYER + "', '" + UserRole.Fields.MEDIATOR + "')")
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile file) {
        FileDocument fileDocument = new FileDocument();
        fileDocument.setId(idGeneratorService.generateId());
        fileDocument.setName(file.getOriginalFilename());
        fileDocument.setContentType(file.getContentType());
        fileRepository.save(fileDocument);
        fileContentStore.setContent(fileDocument, file.getInputStream());
        fileRepository.save(fileDocument);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> get(@PathVariable(name = "fileId") final Long fileId) {
        Optional<FileDocument> optionalDoc = fileRepository.findById(fileId);

        if (optionalDoc.isPresent()) {
            FileDocument doc = optionalDoc.get();
            Resource resource = fileContentStore.getResource(doc);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(doc.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + doc.getName() + "\"")
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }

}
