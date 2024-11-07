package tradingcarbon.my_app.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tradingcarbon.my_app.domain.Messages;
import tradingcarbon.my_app.domain.Order;
import tradingcarbon.my_app.domain.Project;
import tradingcarbon.my_app.domain.ReviewCompany;
import tradingcarbon.my_app.domain.User;
import tradingcarbon.my_app.model.UserDTO;
import tradingcarbon.my_app.repos.ChatsRepository;
import tradingcarbon.my_app.repos.MessagesRepository;
import tradingcarbon.my_app.repos.OrderRepository;
import tradingcarbon.my_app.repos.ProjectRepository;
import tradingcarbon.my_app.repos.ReviewCompanyRepository;
import tradingcarbon.my_app.repos.UserRepository;
import tradingcarbon.my_app.util.NotFoundException;
import tradingcarbon.my_app.util.ReferencedWarning;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ReviewCompanyRepository reviewCompanyRepository;
    private final ChatsRepository chatsRepository;
    private final ProjectRepository projectRepository;
    private final OrderRepository orderRepository;
    private final MessagesRepository messagesRepository;

    public UserService(final UserRepository userRepository,
            final ReviewCompanyRepository reviewCompanyRepository,
            final ChatsRepository chatsRepository, final ProjectRepository projectRepository,
            final OrderRepository orderRepository, final MessagesRepository messagesRepository) {
        this.userRepository = userRepository;
        this.reviewCompanyRepository = reviewCompanyRepository;
        this.chatsRepository = chatsRepository;
        this.projectRepository = projectRepository;
        this.orderRepository = orderRepository;
        this.messagesRepository = messagesRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("userId"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final UUID userId) {
        return userRepository.findById(userId)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getUserId();
    }

    public void update(final UUID userId, final UserDTO userDTO) {
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final UUID userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        chatsRepository.findAllByUserId(user)
                .forEach(chats -> chats.getUserId().remove(user));
        userRepository.delete(user);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setUserId(user.getUserId());
        userDTO.setCompanyName(user.getCompanyName());
        userDTO.setCompanyEmail(user.getCompanyEmail());
        userDTO.setCompanyAddress(user.getCompanyAddress());
        userDTO.setCompanyTaxCode(user.getCompanyTaxCode());
        userDTO.setPassword(user.getPassword());
        userDTO.setCompanySector(user.getCompanySector());
        userDTO.setCompanyAgent(user.getCompanyAgent());
        userDTO.setCompanyAgentPhoneNumber(user.getCompanyAgentPhoneNumber());
        userDTO.setReviewCompanyId(user.getReviewCompanyId() == null ? null : user.getReviewCompanyId().getReviewCompanyId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setCompanyName(userDTO.getCompanyName());
        user.setCompanyEmail(userDTO.getCompanyEmail());
        user.setCompanyAddress(userDTO.getCompanyAddress());
        user.setCompanyTaxCode(userDTO.getCompanyTaxCode());
        user.setPassword(userDTO.getPassword());
        user.setCompanySector(userDTO.getCompanySector());
        user.setCompanyAgent(userDTO.getCompanyAgent());
        user.setCompanyAgentPhoneNumber(userDTO.getCompanyAgentPhoneNumber());
        final ReviewCompany reviewCompanyId = userDTO.getReviewCompanyId() == null ? null : reviewCompanyRepository.findById(userDTO.getReviewCompanyId())
                .orElseThrow(() -> new NotFoundException("reviewCompanyId not found"));
        user.setReviewCompanyId(reviewCompanyId);
        return user;
    }

    public ReferencedWarning getReferencedWarning(final UUID userId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        final Project userIdProject = projectRepository.findFirstByUserId(user);
        if (userIdProject != null) {
            referencedWarning.setKey("user.project.userId.referenced");
            referencedWarning.addParam(userIdProject.getProjectId());
            return referencedWarning;
        }
        final Order sellerIdOrder = orderRepository.findFirstBySellerId(user);
        if (sellerIdOrder != null) {
            referencedWarning.setKey("user.order.sellerId.referenced");
            referencedWarning.addParam(sellerIdOrder.getOrderId());
            return referencedWarning;
        }
        final Order buyerIdOrder = orderRepository.findFirstByBuyerId(user);
        if (buyerIdOrder != null) {
            referencedWarning.setKey("user.order.buyerId.referenced");
            referencedWarning.addParam(buyerIdOrder.getOrderId());
            return referencedWarning;
        }
        final Messages senderIdMessages = messagesRepository.findFirstBySenderId(user);
        if (senderIdMessages != null) {
            referencedWarning.setKey("user.messages.senderId.referenced");
            referencedWarning.addParam(senderIdMessages.getMessageId());
            return referencedWarning;
        }
        return null;
    }

}
