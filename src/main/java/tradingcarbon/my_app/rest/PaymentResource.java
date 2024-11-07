package tradingcarbon.my_app.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
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
import tradingcarbon.my_app.model.PaymentDTO;
import tradingcarbon.my_app.service.PaymentService;
import tradingcarbon.my_app.util.ReferencedException;
import tradingcarbon.my_app.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentResource {

    private final PaymentService paymentService;

    public PaymentResource(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPayment(
            @PathVariable(name = "paymentId") final UUID paymentId) {
        return ResponseEntity.ok(paymentService.get(paymentId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createPayment(@RequestBody @Valid final PaymentDTO paymentDTO) {
        final UUID createdPaymentId = paymentService.create(paymentDTO);
        return new ResponseEntity<>(createdPaymentId, HttpStatus.CREATED);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<UUID> updatePayment(
            @PathVariable(name = "paymentId") final UUID paymentId,
            @RequestBody @Valid final PaymentDTO paymentDTO) {
        paymentService.update(paymentId, paymentDTO);
        return ResponseEntity.ok(paymentId);
    }

    @DeleteMapping("/{paymentId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePayment(
            @PathVariable(name = "paymentId") final UUID paymentId) {
        final ReferencedWarning referencedWarning = paymentService.getReferencedWarning(paymentId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        paymentService.delete(paymentId);
        return ResponseEntity.noContent().build();
    }

}
