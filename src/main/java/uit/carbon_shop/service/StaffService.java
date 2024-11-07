package uit.carbon_shop.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.domain.Staff;
import uit.carbon_shop.model.StaffDTO;
import uit.carbon_shop.repos.OrderRepository;
import uit.carbon_shop.repos.StaffRepository;
import uit.carbon_shop.util.NotFoundException;
import uit.carbon_shop.util.ReferencedWarning;


@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final OrderRepository orderRepository;

    public StaffService(final StaffRepository staffRepository,
            final OrderRepository orderRepository) {
        this.staffRepository = staffRepository;
        this.orderRepository = orderRepository;
    }

    public List<StaffDTO> findAll() {
        final List<Staff> staffs = staffRepository.findAll(Sort.by("staffId"));
        return staffs.stream()
                .map(staff -> mapToDTO(staff, new StaffDTO()))
                .toList();
    }

    public StaffDTO get(final Long staffId) {
        return staffRepository.findById(staffId)
                .map(staff -> mapToDTO(staff, new StaffDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final StaffDTO staffDTO) {
        final Staff staff = new Staff();
        mapToEntity(staffDTO, staff);
        return staffRepository.save(staff).getStaffId();
    }

    public void update(final Long staffId, final StaffDTO staffDTO) {
        final Staff staff = staffRepository.findById(staffId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(staffDTO, staff);
        staffRepository.save(staff);
    }

    public void delete(final Long staffId) {
        staffRepository.deleteById(staffId);
    }

    private StaffDTO mapToDTO(final Staff staff, final StaffDTO staffDTO) {
        staffDTO.setStaffId(staff.getStaffId());
        staffDTO.setEmail(staff.getEmail());
        staffDTO.setPassword(staff.getPassword());
        staffDTO.setStaffName(staff.getStaffName());
        staffDTO.setStaffPhone(staff.getStaffPhone());
        return staffDTO;
    }

    private Staff mapToEntity(final StaffDTO staffDTO, final Staff staff) {
        staff.setEmail(staffDTO.getEmail());
        staff.setPassword(staffDTO.getPassword());
        staff.setStaffName(staffDTO.getStaffName());
        staff.setStaffPhone(staffDTO.getStaffPhone());
        return staff;
    }

    public ReferencedWarning getReferencedWarning(final Long staffId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Staff staff = staffRepository.findById(staffId)
                .orElseThrow(NotFoundException::new);
        final Order staffIdOrder = orderRepository.findFirstByStaffId(staff);
        if (staffIdOrder != null) {
            referencedWarning.setKey("staff.order.staffId.referenced");
            referencedWarning.addParam(staffIdOrder.getOrderId());
            return referencedWarning;
        }
        return null;
    }

}
