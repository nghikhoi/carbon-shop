package uit.carbon_shop.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uit.carbon_shop.domain.Order;
import uit.carbon_shop.domain.User;
import uit.carbon_shop.model.UserDTO;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.repos.OrderRepository;
import uit.carbon_shop.repos.UserRepository;
import uit.carbon_shop.util.NotFoundException;
import uit.carbon_shop.util.ReferencedWarning;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final OrderRepository orderRepository;

    public UserServiceImpl(final UserRepository userRepository,
            final CompanyRepository companyRepository, final PasswordEncoder passwordEncoder,
            final UserMapper userMapper, final OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.orderRepository = orderRepository;
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
        userMapper.updateUser(userDTO, user, companyRepository, passwordEncoder);
        return userRepository.save(user).getUserId();
    }

    @Override
    public void update(final UUID userId, final UserDTO userDTO) {
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        userMapper.updateUser(userDTO, user, companyRepository, passwordEncoder);
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
        final Order createdByOrder = orderRepository.findFirstByCreatedBy(user);
        if (createdByOrder != null) {
            referencedWarning.setKey("user.order.createdBy.referenced");
            referencedWarning.addParam(createdByOrder.getOrderId());
            return referencedWarning;
        }
        return null;
    }

}
