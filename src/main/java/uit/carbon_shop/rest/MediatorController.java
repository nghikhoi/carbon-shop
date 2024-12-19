package uit.carbon_shop.rest;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uit.carbon_shop.model.OrderDTO;
import uit.carbon_shop.model.OrderStatus;


@RestController
@RequestMapping(value = "/mediator/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class MediatorController {

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable(name = "orderId") final String orderId,
            @RequestBody @Valid final OrderStatus orderStatus) {
        return ResponseEntity.ok(null);
    }

}
