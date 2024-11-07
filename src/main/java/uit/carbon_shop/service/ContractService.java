package uit.carbon_shop.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.Contract;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.model.ContractDTO;
import uit.carbon_shop.repos.ContractRepository;
import uit.carbon_shop.repos.OrderRepository;
import uit.carbon_shop.util.NotFoundException;
import uit.carbon_shop.util.ReferencedWarning;


@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final OrderRepository orderRepository;

    public ContractService(final ContractRepository contractRepository,
            final OrderRepository orderRepository) {
        this.contractRepository = contractRepository;
        this.orderRepository = orderRepository;
    }

    public List<ContractDTO> findAll() {
        final List<Contract> contracts = contractRepository.findAll(Sort.by("constractId"));
        return contracts.stream()
                .map(contract -> mapToDTO(contract, new ContractDTO()))
                .toList();
    }

    public ContractDTO get(final Long constractId) {
        return contractRepository.findById(constractId)
                .map(contract -> mapToDTO(contract, new ContractDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ContractDTO contractDTO) {
        final Contract contract = new Contract();
        mapToEntity(contractDTO, contract);
        return contractRepository.save(contract).getConstractId();
    }

    public void update(final Long constractId, final ContractDTO contractDTO) {
        final Contract contract = contractRepository.findById(constractId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(contractDTO, contract);
        contractRepository.save(contract);
    }

    public void delete(final Long constractId) {
        contractRepository.deleteById(constractId);
    }

    private ContractDTO mapToDTO(final Contract contract, final ContractDTO contractDTO) {
        contractDTO.setConstractId(contract.getConstractId());
        contractDTO.setConstractFile(contract.getConstractFile());
        contractDTO.setDateSign(contract.getDateSign());
        return contractDTO;
    }

    private Contract mapToEntity(final ContractDTO contractDTO, final Contract contract) {
        contract.setConstractFile(contractDTO.getConstractFile());
        contract.setDateSign(contractDTO.getDateSign());
        return contract;
    }

    public ReferencedWarning getReferencedWarning(final Long constractId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Contract contract = contractRepository.findById(constractId)
                .orElseThrow(NotFoundException::new);
        final Order constractIdOrder = orderRepository.findFirstByConstractId(contract);
        if (constractIdOrder != null) {
            referencedWarning.setKey("contract.order.constractId.referenced");
            referencedWarning.addParam(constractIdOrder.getOrderId());
            return referencedWarning;
        }
        return null;
    }

}
