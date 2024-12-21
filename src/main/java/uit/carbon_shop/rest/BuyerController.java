package uit.carbon_shop.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.BuyerCreateOrder;
import uit.carbon_shop.model.BuyerReviewCompany;
import uit.carbon_shop.model.BuyerReviewProject;
import uit.carbon_shop.util.UserRoles;


@RestController
@RequestMapping(value = "/api/buyer", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('" + UserRoles.BUYER + "', '" + UserRoles.SELLER + "')")
@SecurityRequirement(name = "bearer-jwt")
public class BuyerController {

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Void> viewProject(
            @PathVariable(name = "projectId") final String projectId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/project/{projectId}/review")
    public ResponseEntity<Void> reviewProject(
            @PathVariable(name = "projectId") final String projectId,
            @RequestBody @Valid final BuyerReviewProject buyerReviewProject) {
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

    @PostMapping("/order")
    public ResponseEntity<Void> newOrder(
            @RequestBody @Valid final BuyerCreateOrder buyerCreateOrder) {
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
            @RequestBody @Valid final BuyerReviewCompany buyerReviewCompany) {
        return ResponseEntity.ok().build();
    }

}
