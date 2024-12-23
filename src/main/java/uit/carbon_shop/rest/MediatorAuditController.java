package uit.carbon_shop.rest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.AppUserDTO;
import uit.carbon_shop.model.MediatorAnswerDTO;
import uit.carbon_shop.model.MediatorApproveProjectDTO;
import uit.carbon_shop.model.MediatorApproveUserDTO;
import uit.carbon_shop.model.MediatorCancelOrderDTO;
import uit.carbon_shop.model.MediatorDoneOrderDTO;
import uit.carbon_shop.model.MediatorProcessOrderDTO;
import uit.carbon_shop.model.MediatorRejectProjectDTO;
import uit.carbon_shop.model.MediatorRejectUserDTO;
import uit.carbon_shop.model.OrderDTO;
import uit.carbon_shop.model.OrderStatus;
import uit.carbon_shop.model.PagedAppUserDTO;
import uit.carbon_shop.model.PagedProjectDTO;
import uit.carbon_shop.model.PagedQuestionDTO;
import uit.carbon_shop.model.ProjectStatus;
import uit.carbon_shop.model.UserRole;
import uit.carbon_shop.model.UserStatus;
import uit.carbon_shop.service.AppUserService;
import uit.carbon_shop.service.OrderService;
import uit.carbon_shop.service.ProjectService;
import uit.carbon_shop.service.QuestionService;


@RestController
@RequestMapping(value = "/api/mediator/audit", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('" + UserRole.Fields.MEDIATOR + "')")
@SecurityRequirement(name = "bearer-jwt")
@RequiredArgsConstructor
public class MediatorAuditController {

    private final OrderService orderService;
    private final AppUserService appUserService;
    private final ProjectService projectService;
    private final QuestionService questionService;

    @PatchMapping("/order/{orderId}/process")
    public ResponseEntity<OrderDTO> startProcessOrder(
            @PathVariable(name = "orderId") final Long orderId) {
        var order = orderService.get(orderId);
        order.setStatus(OrderStatus.PROCESSING);
        orderService.update(orderId, order);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/order/{orderId}/cancel")
    public ResponseEntity<OrderDTO> cancelProcessOrder(
            @PathVariable(name = "orderId") final Long orderId) {
        var order = orderService.get(orderId);
        order.setStatus(OrderStatus.CANCELLED);
        orderService.update(orderId, order);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/order/{orderId}/done")
    public ResponseEntity<OrderDTO> doneProcessOrder(
            @PathVariable(name = "orderId") final Long orderId) {
        var order = orderService.get(orderId);
        order.setStatus(OrderStatus.DONE);
        orderService.update(orderId, order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/users/init")
    public ResponseEntity<PagedAppUserDTO> viewAllUser(
            @Parameter(hidden = true) @SortDefault(sort = "userId") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(new PagedAppUserDTO(appUserService.findByStatus(UserStatus.INIT, pageable)));
    }

    @PatchMapping("/user/{userId}/approve")
    public ResponseEntity<AppUserDTO> approveUserRegistration(
            @PathVariable(name = "userId") final Long userId) {
        var appUser = appUserService.get(userId);
        appUser.setApprovedAt(LocalDateTime.now());
        appUser.setStatus(UserStatus.APPROVED);
        appUserService.update(userId, appUser);
        return ResponseEntity.ok(appUser);
    }

    @PatchMapping("/user/{userId}/reject")
    public ResponseEntity<AppUserDTO> rejectUserRegistration(
            @PathVariable(name = "userId") final Long userId) {
        var appUser = appUserService.get(userId);
        appUser.setRejectedAt(LocalDateTime.now());
        appUser.setStatus(UserStatus.REJECTED);
        appUserService.update(userId, appUser);
        return ResponseEntity.ok(appUser);
    }

    @GetMapping("/projects/init")
    public ResponseEntity<PagedProjectDTO> viewAllProject(
            @Parameter(hidden = true) @SortDefault(sort = "projectId") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(new PagedProjectDTO(projectService.findByStatus(ProjectStatus.INIT, pageable)));
    }

    @PatchMapping("/project/{projectId}/approve")
    public ResponseEntity<Void> approveProject(
            @PathVariable(name = "projectId") final Long projectId,
            Authentication authentication) {
        var userId = Long.parseLong(authentication.getName());
        var project = projectService.get(projectId);
        project.setStatus(ProjectStatus.APPROVED);
        project.setAuditBy(userId);
        projectService.update(projectId, project);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/project/{projectId}/reject")
    public ResponseEntity<Void> rejectProject(
            @PathVariable(name = "projectId") final Long projectId) {
        var project = projectService.get(projectId);
        project.setStatus(ProjectStatus.REJECTED);
        projectService.update(projectId, project);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/questions/init")
    public ResponseEntity<PagedQuestionDTO> viewAllQuestion(
            @Parameter(hidden = true) @SortDefault(sort = "questionId") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(new PagedQuestionDTO(questionService.findByAnswerIsNull(pageable)));
    }

    @PatchMapping("/question/{questionId}")
    public ResponseEntity<Void> answerQuestion(
            @PathVariable(name = "questionId") final Long questionId,
            @RequestBody @Valid final MediatorAnswerDTO mediatorAnswerDTO) {
        var question = questionService.get(questionId);
        question.setAnswer(mediatorAnswerDTO.getAnswer());
        questionService.update(questionId, question);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/question/{questionId}/answer")
    public ResponseEntity<Void> deleteQuestionAnswer(
            @PathVariable(name = "questionId") final Long questionId) {
        var question = questionService.get(questionId);
        question.setAnswer(null);
        questionService.update(questionId, question);
        return ResponseEntity.ok().build();
    }

}
