package tradingcarbon.my_app.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tradingcarbon.my_app.domain.Order;
import tradingcarbon.my_app.domain.OrderStatus;
import tradingcarbon.my_app.domain.Project;
import tradingcarbon.my_app.model.OrderStatusDTO;
import tradingcarbon.my_app.repos.OrderRepository;
import tradingcarbon.my_app.repos.OrderStatusRepository;
import tradingcarbon.my_app.repos.ProjectRepository;
import tradingcarbon.my_app.util.NotFoundException;
import tradingcarbon.my_app.util.ReferencedWarning;


@Service
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;
    private final ProjectRepository projectRepository;
    private final OrderRepository orderRepository;

    public OrderStatusService(final OrderStatusRepository orderStatusRepository,
            final ProjectRepository projectRepository, final OrderRepository orderRepository) {
        this.orderStatusRepository = orderStatusRepository;
        this.projectRepository = projectRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderStatusDTO> findAll() {
        final List<OrderStatus> orderStatuses = orderStatusRepository.findAll(Sort.by("orderStatusId"));
        return orderStatuses.stream()
                .map(orderStatus -> mapToDTO(orderStatus, new OrderStatusDTO()))
                .toList();
    }

    public OrderStatusDTO get(final Long orderStatusId) {
        return orderStatusRepository.findById(orderStatusId)
                .map(orderStatus -> mapToDTO(orderStatus, new OrderStatusDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderStatusDTO orderStatusDTO) {
        final OrderStatus orderStatus = new OrderStatus();
        mapToEntity(orderStatusDTO, orderStatus);
        return orderStatusRepository.save(orderStatus).getOrderStatusId();
    }

    public void update(final Long orderStatusId, final OrderStatusDTO orderStatusDTO) {
        final OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderStatusDTO, orderStatus);
        orderStatusRepository.save(orderStatus);
    }

    public void delete(final Long orderStatusId) {
        orderStatusRepository.deleteById(orderStatusId);
    }

    private OrderStatusDTO mapToDTO(final OrderStatus orderStatus,
            final OrderStatusDTO orderStatusDTO) {
        orderStatusDTO.setOrderStatusId(orderStatus.getOrderStatusId());
        orderStatusDTO.setBuyerId(orderStatus.getBuyerId());
        orderStatusDTO.setDateCreate(orderStatus.getDateCreate());
        orderStatusDTO.setOrderStatus(orderStatus.getOrderStatus());
        orderStatusDTO.setProjectId(orderStatus.getProjectId() == null ? null : orderStatus.getProjectId().getProjectId());
        return orderStatusDTO;
    }

    private OrderStatus mapToEntity(final OrderStatusDTO orderStatusDTO,
            final OrderStatus orderStatus) {
        orderStatus.setBuyerId(orderStatusDTO.getBuyerId());
        orderStatus.setDateCreate(orderStatusDTO.getDateCreate());
        orderStatus.setOrderStatus(orderStatusDTO.getOrderStatus());
        final Project projectId = orderStatusDTO.getProjectId() == null ? null : projectRepository.findById(orderStatusDTO.getProjectId())
                .orElseThrow(() -> new NotFoundException("projectId not found"));
        orderStatus.setProjectId(projectId);
        return orderStatus;
    }

    public ReferencedWarning getReferencedWarning(final Long orderStatusId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId)
                .orElseThrow(NotFoundException::new);
        final Order orderStatusIdOrder = orderRepository.findFirstByOrderStatusId(orderStatus);
        if (orderStatusIdOrder != null) {
            referencedWarning.setKey("orderStatus.order.orderStatusId.referenced");
            referencedWarning.addParam(orderStatusIdOrder.getOrderId());
            return referencedWarning;
        }
        return null;
    }

}
