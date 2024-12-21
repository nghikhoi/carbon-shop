package uit.carbon_shop.service;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uit.carbon_shop.domain.AppUser;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.model.OrderDTO;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.repos.ProjectRepository;
import uit.carbon_shop.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "processBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    OrderDTO updateOrderDTO(Order order, @MappingTarget OrderDTO orderDTO);

    @AfterMapping
    default void afterUpdateOrderDTO(Order order, @MappingTarget OrderDTO orderDTO) {
        orderDTO.setProject(order.getProject() == null ? null : order.getProject().getProjectId());
        orderDTO.setProcessBy(order.getProcessBy() == null ? null : order.getProcessBy().getUserId());
        orderDTO.setCreatedBy(order.getCreatedBy() == null ? null : order.getCreatedBy().getUserId());
    }

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "processBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Order updateOrder(OrderDTO orderDTO, @MappingTarget Order order,
            @Context ProjectRepository projectRepository,
            @Context AppUserRepository appUserRepository,
            @Context AppUserRepository appUserRepository);

    @AfterMapping
    default void afterUpdateOrder(OrderDTO orderDTO, @MappingTarget Order order,
            @Context ProjectRepository projectRepository,
            @Context AppUserRepository appUserRepository,
            @Context AppUserRepository appUserRepository) {
        final Project project = orderDTO.getProject() == null ? null : projectRepository.findById(orderDTO.getProject())
                .orElseThrow(() -> new NotFoundException("project not found"));
        order.setProject(project);
        final AppUser processBy = orderDTO.getProcessBy() == null ? null : appUserRepository.findById(orderDTO.getProcessBy())
                .orElseThrow(() -> new NotFoundException("processBy not found"));
        order.setProcessBy(processBy);
        final AppUser createdBy = orderDTO.getCreatedBy() == null ? null : appUserRepository.findById(orderDTO.getCreatedBy())
                .orElseThrow(() -> new NotFoundException("createdBy not found"));
        order.setCreatedBy(createdBy);
    }

}
