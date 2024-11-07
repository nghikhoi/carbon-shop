package uit.carbon_shop.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.domain.Payment;
import uit.carbon_shop.model.PaymentDTO;
import uit.carbon_shop.repos.OrderRepository;
import uit.carbon_shop.repos.PaymentRepository;
import uit.carbon_shop.util.NotFoundException;
import uit.carbon_shop.util.ReferencedWarning;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(final PaymentRepository paymentRepository,
            final OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public List<PaymentDTO> findAll() {
        final List<Payment> payments = paymentRepository.findAll(Sort.by("paymentId"));
        return payments.stream()
                .map(payment -> mapToDTO(payment, new PaymentDTO()))
                .toList();
    }

    public PaymentDTO get(final UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .map(payment -> mapToDTO(payment, new PaymentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final PaymentDTO paymentDTO) {
        final Payment payment = new Payment();
        mapToEntity(paymentDTO, payment);
        return paymentRepository.save(payment).getPaymentId();
    }

    public void update(final UUID paymentId, final PaymentDTO paymentDTO) {
        final Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(paymentDTO, payment);
        paymentRepository.save(payment);
    }

    public void delete(final UUID paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    private PaymentDTO mapToDTO(final Payment payment, final PaymentDTO paymentDTO) {
        paymentDTO.setPaymentId(payment.getPaymentId());
        paymentDTO.setDatePayment(payment.getDatePayment());
        paymentDTO.setTotal(payment.getTotal());
        paymentDTO.setPaymentNumber(payment.getPaymentNumber());
        return paymentDTO;
    }

    private Payment mapToEntity(final PaymentDTO paymentDTO, final Payment payment) {
        payment.setDatePayment(paymentDTO.getDatePayment());
        payment.setTotal(paymentDTO.getTotal());
        payment.setPaymentNumber(paymentDTO.getPaymentNumber());
        return payment;
    }

    public ReferencedWarning getReferencedWarning(final UUID paymentId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(NotFoundException::new);
        final Order paymentIdOrder = orderRepository.findFirstByPaymentId(payment);
        if (paymentIdOrder != null) {
            referencedWarning.setKey("payment.order.paymentId.referenced");
            referencedWarning.addParam(paymentIdOrder.getOrderId());
            return referencedWarning;
        }
        return null;
    }

}
