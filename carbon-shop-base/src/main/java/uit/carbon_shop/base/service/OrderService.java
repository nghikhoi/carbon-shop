package uit.carbon_shop.base.service;

import java.util.List;
import uit.carbon_shop.base.model.OrderDTO;


public interface OrderService {

    List<OrderDTO> findAll();

    OrderDTO get(Long orderId);

    Long create(OrderDTO orderDTO);

    void update(Long orderId, OrderDTO orderDTO);

    void delete(Long orderId);

}
