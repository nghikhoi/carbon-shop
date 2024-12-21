package uit.carbon_shop.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.model.OrderDTO;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.repos.OrderRepository;
import uit.carbon_shop.repos.ProjectRepository;
import uit.carbon_shop.util.NotFoundException;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(final OrderRepository orderRepository,
            final ProjectRepository projectRepository, final AppUserRepository appUserRepository,
            final OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.projectRepository = projectRepository;
        this.appUserRepository = appUserRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("orderId"));
        return orders.stream()
                .map(order -> orderMapper.updateOrderDTO(order, new OrderDTO()))
                .toList();
    }

    @Override
    public OrderDTO get(final Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> orderMapper.updateOrderDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        orderMapper.updateOrder(orderDTO, order, projectRepository, appUserRepository, appUserRepository);
        return orderRepository.save(order).getOrderId();
    }

    @Override
    public void update(final Long orderId, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        orderMapper.updateOrder(orderDTO, order, projectRepository, appUserRepository, appUserRepository);
        orderRepository.save(order);
    }

    @Override
    public void delete(final Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
