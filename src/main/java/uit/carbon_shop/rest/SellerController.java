package uit.carbon_shop.rest;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.ProjectDTO;
import uit.carbon_shop.model.SellerRegisterProjectDTO;
import uit.carbon_shop.model.SellerReviewCompany;


@RestController
@RequestMapping(value = "/api/seller", produces = MediaType.APPLICATION_JSON_VALUE)
public class SellerController {

    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> registerProject(
            @RequestBody @Valid final SellerRegisterProjectDTO sellerRegisterProjectDTO) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Void> viewProject(
            @PathVariable(name = "projectId") final String projectId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/projects")
    public ResponseEntity<Void> viewAllProject() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Void> viewOrder(@PathVariable(name = "orderId") final String orderId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<Void> viewAllOrders() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Void> viewCompany(
            @PathVariable(name = "companyId") final String companyId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/company/{companyId}/review")
    public ResponseEntity<Void> reviewCompany(
            @PathVariable(name = "companyId") final String companyId,
            @RequestBody @Valid final SellerReviewCompany sellerReviewCompany) {
        return ResponseEntity.ok().build();
    }

}
