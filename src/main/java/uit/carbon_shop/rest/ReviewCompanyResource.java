package uit.carbon_shop.rest;

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
import uit.carbon_shop.model.ReviewCompanyDTO;
import uit.carbon_shop.service.ReviewCompanyService;
import uit.carbon_shop.util.ReferencedException;
import uit.carbon_shop.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/reviewCompanies", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewCompanyResource {

    private final ReviewCompanyService reviewCompanyService;

    public ReviewCompanyResource(final ReviewCompanyService reviewCompanyService) {
        this.reviewCompanyService = reviewCompanyService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewCompanyDTO>> getAllReviewCompanies() {
        return ResponseEntity.ok(reviewCompanyService.findAll());
    }

    @GetMapping("/{reviewCompanyId}")
    public ResponseEntity<ReviewCompanyDTO> getReviewCompany(
            @PathVariable(name = "reviewCompanyId") final Long reviewCompanyId) {
        return ResponseEntity.ok(reviewCompanyService.get(reviewCompanyId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createReviewCompany(
            @RequestBody @Valid final ReviewCompanyDTO reviewCompanyDTO) {
        final Long createdReviewCompanyId = reviewCompanyService.create(reviewCompanyDTO);
        return new ResponseEntity<>(createdReviewCompanyId, HttpStatus.CREATED);
    }

    @PutMapping("/{reviewCompanyId}")
    public ResponseEntity<Long> updateReviewCompany(
            @PathVariable(name = "reviewCompanyId") final Long reviewCompanyId,
            @RequestBody @Valid final ReviewCompanyDTO reviewCompanyDTO) {
        reviewCompanyService.update(reviewCompanyId, reviewCompanyDTO);
        return ResponseEntity.ok(reviewCompanyId);
    }

    @DeleteMapping("/{reviewCompanyId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteReviewCompany(
            @PathVariable(name = "reviewCompanyId") final Long reviewCompanyId) {
        final ReferencedWarning referencedWarning = reviewCompanyService.getReferencedWarning(reviewCompanyId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        reviewCompanyService.delete(reviewCompanyId);
        return ResponseEntity.noContent().build();
    }

}
