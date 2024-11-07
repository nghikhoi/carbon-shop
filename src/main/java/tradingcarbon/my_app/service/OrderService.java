package tradingcarbon.my_app.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tradingcarbon.my_app.domain.Contract;
import tradingcarbon.my_app.domain.Order;
import tradingcarbon.my_app.domain.OrderStatus;
import tradingcarbon.my_app.domain.Payment;
import tradingcarbon.my_app.domain.Staff;
import tradingcarbon.my_app.domain.User;
import tradingcarbon.my_app.model.OrderDTO;
import tradingcarbon.my_app.repos.ContractRepository;
import tradingcarbon.my_app.repos.OrderRepository;
import tradingcarbon.my_app.repos.OrderStatusRepository;
import tradingcarbon.my_app.repos.PaymentRepository;
import tradingcarbon.my_app.repos.StaffRepository;
import tradingcarbon.my_app.repos.UserRepository;
import tradingcarbon.my_app.util.NotFoundException;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ContractRepository contractRepository;
    private final StaffRepository staffRepository;

    public OrderService(final OrderRepository orderRepository,
            final OrderStatusRepository orderStatusRepository, final UserRepository userRepository,
            final PaymentRepository paymentRepository, final ContractRepository contractRepository,
            final StaffRepository staffRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.contractRepository = contractRepository;
        this.staffRepository = staffRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("orderId"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getOrderId();
    }

    public void update(final Long orderId, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setNumberCredits(order.getNumberCredits());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setOrderStatusId(order.getOrderStatusId() == null ? null : order.getOrderStatusId().getOrderStatusId());
        orderDTO.setSellerId(order.getSellerId() == null ? null : order.getSellerId().getUserId());
        orderDTO.setBuyerId(order.getBuyerId() == null ? null : order.getBuyerId().getUserId());
        orderDTO.setPaymentId(order.getPaymentId() == null ? null : order.getPaymentId().getPaymentId());
        orderDTO.setConstractId(order.getConstractId() == null ? null : order.getConstractId().getConstractId());
        orderDTO.setStaffId(order.getStaffId() == null ? null : order.getStaffId().getStaffId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setNumberCredits(orderDTO.getNumberCredits());
        order.setPrice(orderDTO.getPrice());
        order.setTotal(orderDTO.getTotal());
        final OrderStatus orderStatusId = orderDTO.getOrderStatusId() == null ? null : orderStatusRepository.findById(orderDTO.getOrderStatusId())
                .orElseThrow(() -> new NotFoundException("orderStatusId not found"));
        order.setOrderStatusId(orderStatusId);
        final User sellerId = orderDTO.getSellerId() == null ? null : userRepository.findById(orderDTO.getSellerId())
                .orElseThrow(() -> new NotFoundException("sellerId not found"));
        order.setSellerId(sellerId);
        final User buyerId = orderDTO.getBuyerId() == null ? null : userRepository.findById(orderDTO.getBuyerId())
                .orElseThrow(() -> new NotFoundException("buyerId not found"));
        order.setBuyerId(buyerId);
        final Payment paymentId = orderDTO.getPaymentId() == null ? null : paymentRepository.findById(orderDTO.getPaymentId())
                .orElseThrow(() -> new NotFoundException("paymentId not found"));
        order.setPaymentId(paymentId);
        final Contract constractId = orderDTO.getConstractId() == null ? null : contractRepository.findById(orderDTO.getConstractId())
                .orElseThrow(() -> new NotFoundException("constractId not found"));
        order.setConstractId(constractId);
        final Staff staffId = orderDTO.getStaffId() == null ? null : staffRepository.findById(orderDTO.getStaffId())
                .orElseThrow(() -> new NotFoundException("staffId not found"));
        order.setStaffId(staffId);
        return order;
    }

    public boolean orderStatusIdExists(final Long orderStatusId) {
        return orderRepository.existsByOrderStatusIdOrderStatusId(orderStatusId);
    }

    public boolean paymentIdExists(final UUID paymentId) {
        return orderRepository.existsByPaymentIdPaymentId(paymentId);
    }

    public boolean constractIdExists(final Long constractId) {
        return orderRepository.existsByConstractIdConstractId(constractId);
    }

}
