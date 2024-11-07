package tradingcarbon.my_app.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import tradingcarbon.my_app.model.ReviewProjectDTO;
import tradingcarbon.my_app.service.ReviewProjectService;
import tradingcarbon.my_app.util.ReferencedException;
import tradingcarbon.my_app.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/reviewProjects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewProjectResource {

    private final ReviewProjectService reviewProjectService;

    public ReviewProjectResource(final ReviewProjectService reviewProjectService) {
        this.reviewProjectService = reviewProjectService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewProjectDTO>> getAllReviewProjects() {
        return ResponseEntity.ok(reviewProjectService.findAll());
    }

    @GetMapping("/{reviewProjectId}")
    public ResponseEntity<ReviewProjectDTO> getReviewProject(
            @PathVariable(name = "reviewProjectId") final Long reviewProjectId) {
        return ResponseEntity.ok(reviewProjectService.get(reviewProjectId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createReviewProject(
            @RequestBody @Valid final ReviewProjectDTO reviewProjectDTO) {
        final Long createdReviewProjectId = reviewProjectService.create(reviewProjectDTO);
        return new ResponseEntity<>(createdReviewProjectId, HttpStatus.CREATED);
    }

    @PutMapping("/{reviewProjectId}")
    public ResponseEntity<Long> updateReviewProject(
            @PathVariable(name = "reviewProjectId") final Long reviewProjectId,
            @RequestBody @Valid final ReviewProjectDTO reviewProjectDTO) {
        reviewProjectService.update(reviewProjectId, reviewProjectDTO);
        return ResponseEntity.ok(reviewProjectId);
    }

    @DeleteMapping("/{reviewProjectId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteReviewProject(
            @PathVariable(name = "reviewProjectId") final Long reviewProjectId) {
        final ReferencedWarning referencedWarning = reviewProjectService.getReferencedWarning(reviewProjectId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        reviewProjectService.delete(reviewProjectId);
        return ResponseEntity.noContent().build();
    }

}
