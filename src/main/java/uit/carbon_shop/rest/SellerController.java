package uit.carbon_shop.rest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.AppUserDTO;
import uit.carbon_shop.model.CompanyDTO;
import uit.carbon_shop.model.CompanyReviewDTO;
import uit.carbon_shop.model.OrderDTO;
import uit.carbon_shop.model.ProjectDTO;
import uit.carbon_shop.model.ProjectStatus;
import uit.carbon_shop.model.SellerRegisterProjectDTO;
import uit.carbon_shop.model.SellerReviewCompany;
import uit.carbon_shop.model.UserRole;
import uit.carbon_shop.service.AppUserService;
import uit.carbon_shop.service.CompanyReviewService;
import uit.carbon_shop.service.CompanyService;
import uit.carbon_shop.service.IdGeneratorService;
import uit.carbon_shop.service.OrderService;
import uit.carbon_shop.service.ProjectService;

@RestController
@RequestMapping(value = "/api/seller", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('" + UserRole.Fields.SELLER_OR_BUYER + "')")
@SecurityRequirement(name = "bearer-jwt")
@RequiredArgsConstructor
public class SellerController {

    private final ProjectService projectService;
    private final OrderService orderService;
    private final CompanyReviewService companyReviewService;
    private final CompanyService companyService;
    private final AppUserService appUserService;
    private final IdGeneratorService idGeneratorService;

    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> registerProject(
            @RequestBody @Valid final SellerRegisterProjectDTO sellerRegisterProjectDTO,
            Authentication authentication) {
        long userId = Long.parseLong(authentication.getName());
        AppUserDTO appUser = appUserService.get(userId);
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(idGeneratorService.generateId());
        projectDTO.setName(sellerRegisterProjectDTO.getName());
        projectDTO.setAddress(sellerRegisterProjectDTO.getAddress());
        projectDTO.setSize(sellerRegisterProjectDTO.getSize());
        projectDTO.setTimeStart(sellerRegisterProjectDTO.getTimeStart());
        projectDTO.setTimeEnd(sellerRegisterProjectDTO.getTimeEnd());
        projectDTO.setProduceCarbonRate(sellerRegisterProjectDTO.getProduceCarbonRate());
        projectDTO.setPartner(sellerRegisterProjectDTO.getPartner());
        projectDTO.setAuditByOrg(sellerRegisterProjectDTO.getAuditByOrg());
        projectDTO.setCreditAmount(sellerRegisterProjectDTO.getCreditAmount());
        projectDTO.setCert(sellerRegisterProjectDTO.getCert());
        projectDTO.setPrice(sellerRegisterProjectDTO.getPrice());
        projectDTO.setMethodPayment(sellerRegisterProjectDTO.getMethodPayment());
        projectDTO.setProjectImages(sellerRegisterProjectDTO.getProjectImages());
        projectDTO.setOwnerCompany(appUser.getCompany());
        projectDTO.setStatus(ProjectStatus.INIT);
        projectService.create(projectDTO);
        return ResponseEntity.ok(projectService.get(projectDTO.getProjectId()));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectDTO> viewProject(
            @PathVariable(name = "projectId") final Long projectId) {
        return ResponseEntity.ok(projectService.get(projectId));
    }

    @GetMapping("/projects")
    public ResponseEntity<Page<ProjectDTO>> viewAllProject(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "projectId") @PageableDefault(size = 20) final Pageable pageable,
            Authentication authentication) {
        long userId = Long.parseLong(authentication.getName());
        AppUserDTO appUser = appUserService.get(userId);
        return ResponseEntity.ok(projectService.findAllByOwner(appUser.getCompany(), pageable));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> viewOrder(
            @PathVariable(name = "orderId") final Long orderId) {
        return ResponseEntity.ok(orderService.get(orderId));
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDTO>> viewAllOrders(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "orderId") @PageableDefault(size = 20) final Pageable pageable,
            Authentication authentication) {
        long userId = Long.parseLong(authentication.getName());
        AppUserDTO appUser = appUserService.get(userId);
        return ResponseEntity.ok(orderService.findByOwnerCompany(appUser.getCompany(), pageable));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<CompanyDTO> viewCompany(
            @PathVariable(name = "companyId") final Long companyId) {
        return ResponseEntity.ok(companyService.get(companyId));
    }

    @PostMapping("/company/{companyId}/review")
    public ResponseEntity<Void> reviewCompany(
            @PathVariable(name = "companyId") final Long companyId,
            @RequestBody @Valid final SellerReviewCompany sellerReviewCompany,
            Authentication authentication) {
        CompanyReviewDTO companyReviewDTO = new CompanyReviewDTO();
        companyReviewDTO.setId(idGeneratorService.generateId());
        companyReviewDTO.setMessage(sellerReviewCompany.getMessage());
        companyReviewDTO.setRate(sellerReviewCompany.getRate());
        companyReviewDTO.setImages(sellerReviewCompany.getImages());
        companyReviewDTO.setCompany(companyId);
        companyReviewDTO.setReviewBy(Long.parseLong(authentication.getName()));
        companyReviewService.create(companyReviewDTO);
        return ResponseEntity.ok().build();
    }

}
