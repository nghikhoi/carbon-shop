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
import tradingcarbon.my_app.model.OrderStatusDTO;
import tradingcarbon.my_app.service.OrderStatusService;
import tradingcarbon.my_app.util.ReferencedException;
import tradingcarbon.my_app.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/orderStatuses", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderStatusResource {

    private final OrderStatusService orderStatusService;

    public OrderStatusResource(final OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @GetMapping
    public ResponseEntity<List<OrderStatusDTO>> getAllOrderStatuses() {
        return ResponseEntity.ok(orderStatusService.findAll());
    }

    @GetMapping("/{orderStatusId}")
    public ResponseEntity<OrderStatusDTO> getOrderStatus(
            @PathVariable(name = "orderStatusId") final Long orderStatusId) {
        return ResponseEntity.ok(orderStatusService.get(orderStatusId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOrderStatus(
            @RequestBody @Valid final OrderStatusDTO orderStatusDTO) {
        final Long createdOrderStatusId = orderStatusService.create(orderStatusDTO);
        return new ResponseEntity<>(createdOrderStatusId, HttpStatus.CREATED);
    }

    @PutMapping("/{orderStatusId}")
    public ResponseEntity<Long> updateOrderStatus(
            @PathVariable(name = "orderStatusId") final Long orderStatusId,
            @RequestBody @Valid final OrderStatusDTO orderStatusDTO) {
        orderStatusService.update(orderStatusId, orderStatusDTO);
        return ResponseEntity.ok(orderStatusId);
    }

    @DeleteMapping("/{orderStatusId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOrderStatus(
            @PathVariable(name = "orderStatusId") final Long orderStatusId) {
        final ReferencedWarning referencedWarning = orderStatusService.getReferencedWarning(orderStatusId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        orderStatusService.delete(orderStatusId);
        return ResponseEntity.noContent().build();
    }

}
