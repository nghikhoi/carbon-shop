package uit.carbon_shop.service;

import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.CompanyReview;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.domain.Project;
import uit.carbon_shop.domain.ProjectReview;
import uit.carbon_shop.domain.User;
import uit.carbon_shop.model.UserDTO;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.repos.CompanyReviewRepository;
import uit.carbon_shop.repos.OrderRepository;
import uit.carbon_shop.repos.ProjectRepository;
import uit.carbon_shop.repos.ProjectReviewRepository;
import uit.carbon_shop.repos.UserRepository;
import uit.carbon_shop.util.NotFoundException;
import uit.carbon_shop.util.ReferencedWarning;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final OrderRepository orderRepository;
    private final CompanyReviewRepository companyReviewRepository;
    private final ProjectReviewRepository projectReviewRepository;

    public UserServiceImpl(final UserRepository userRepository,
            final CompanyRepository companyRepository, final ProjectRepository projectRepository,
            final PasswordEncoder passwordEncoder, final UserMapper userMapper,
            final OrderRepository orderRepository,
            final CompanyReviewRepository companyReviewRepository,
            final ProjectReviewRepository projectReviewRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.orderRepository = orderRepository;
        this.companyReviewRepository = companyReviewRepository;
        this.projectReviewRepository = projectReviewRepository;
    }

    @Override
    public Page<UserDTO> findAll(final String filter, final Pageable pageable) {
        Page<User> page;
        if (filter != null) {
            UUID uuidFilter = null;
            try {
                uuidFilter = UUID.fromString(filter);
            } catch (final IllegalArgumentException illegalArgumentException) {
                // keep null - no parseable input
            }
            page = userRepository.findAllByUserId(uuidFilter, pageable);
        } else {
            page = userRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(user -> userMapper.updateUserDTO(user, new UserDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public UserDTO get(final UUID userId) {
        return userRepository.findById(userId)
                .map(user -> userMapper.updateUserDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public UUID create(final UserDTO userDTO) {
        final User user = new User();
        userMapper.updateUser(userDTO, user, companyRepository, projectRepository, passwordEncoder);
        return userRepository.save(user).getUserId();
    }

    @Override
    public void update(final UUID userId, final UserDTO userDTO) {
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        userMapper.updateUser(userDTO, user, companyRepository, projectRepository, passwordEncoder);
        userRepository.save(user);
    }

    @Override
    public void delete(final UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean companyExists(final UUID id) {
        return userRepository.existsByCompanyId(id);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final UUID userId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        final Project auditByProject = projectRepository.findFirstByAuditBy(user);
        if (auditByProject != null) {
            referencedWarning.setKey("user.project.auditBy.referenced");
            referencedWarning.addParam(auditByProject.getProjectId());
            return referencedWarning;
        }
        final Order processByOrder = orderRepository.findFirstByProcessBy(user);
        if (processByOrder != null) {
            referencedWarning.setKey("user.order.processBy.referenced");
            referencedWarning.addParam(processByOrder.getOrderId());
            return referencedWarning;
        }
        final Order createdByOrder = orderRepository.findFirstByCreatedBy(user);
        if (createdByOrder != null) {
            referencedWarning.setKey("user.order.createdBy.referenced");
            referencedWarning.addParam(createdByOrder.getOrderId());
            return referencedWarning;
        }
        final CompanyReview reviewByCompanyReview = companyReviewRepository.findFirstByReviewBy(user);
        if (reviewByCompanyReview != null) {
            referencedWarning.setKey("user.companyReview.reviewBy.referenced");
            referencedWarning.addParam(reviewByCompanyReview.getId());
            return referencedWarning;
        }
        final ProjectReview reviewByProjectReview = projectReviewRepository.findFirstByReviewBy(user);
        if (reviewByProjectReview != null) {
            referencedWarning.setKey("user.projectReview.reviewBy.referenced");
            referencedWarning.addParam(reviewByProjectReview.getId());
            return referencedWarning;
        }
        return null;
    }

}
