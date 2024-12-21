package uit.carbon_shop.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import uit.carbon_shop.model.UserRole;


@RestController
@RequestMapping(value = "/api/mediator/audit", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('" + UserRole.Fields.MEDIATOR + "')")
@SecurityRequirement(name = "bearer-jwt")
public class MediatorAuditController {

    @PatchMapping("/order/{orderId}/process")
    public ResponseEntity<OrderDTO> startProcessOrder(
            @PathVariable(name = "orderId") final String orderId,
            @RequestBody @Valid final MediatorProcessOrderDTO mediatorProcessOrderDTO) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/order/{orderId}/cancel")
    public ResponseEntity<OrderDTO> cancelProcessOrder(
            @PathVariable(name = "orderId") final String orderId,
            @RequestBody @Valid final MediatorCancelOrderDTO mediatorCancelOrderDTO) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/order/{orderId}/done")
    public ResponseEntity<OrderDTO> doneProcessOrder(
            @PathVariable(name = "orderId") final String orderId,
            @RequestBody @Valid final MediatorDoneOrderDTO mediatorDoneOrderDTO) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/user/{userId}/approve")
    public ResponseEntity<AppUserDTO> approveUserRegistration(
            @PathVariable(name = "userId") final String userId,
            @RequestBody @Valid final MediatorApproveUserDTO mediatorApproveUserDTO) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/user/{userId}/reject")
    public ResponseEntity<AppUserDTO> rejectUserRegistration(
            @PathVariable(name = "userId") final String userId,
            @RequestBody @Valid final MediatorRejectUserDTO mediatorRejectUserDTO) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/project/{projectId}/approve")
    public ResponseEntity<Void> approveProject(
            @PathVariable(name = "projectId") final String projectId,
            @RequestBody @Valid final MediatorApproveProjectDTO mediatorApproveProjectDTO) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/project/{projectId}/reject")
    public ResponseEntity<Void> rejectProject(
            @PathVariable(name = "projectId") final String projectId,
            @RequestBody @Valid final MediatorRejectProjectDTO mediatorRejectProjectDTO) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/question/{questionId}")
    public ResponseEntity<Void> answerQuestion(
            @PathVariable(name = "questionId") final String questionId,
            @RequestBody @Valid final MediatorAnswerDTO mediatorAnswerDTO) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/question/{questionId}/answer")
    public ResponseEntity<Void> deleteQuestionAnswer(
            @PathVariable(name = "questionId") final String questionId) {
        return ResponseEntity.ok().build();
    }

}
