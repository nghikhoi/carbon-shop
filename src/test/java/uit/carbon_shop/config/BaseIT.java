package uit.carbon_shop.config;

import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import uit.carbon_shop.CarbonShopApplication;
import uit.carbon_shop.repos.ChatParticipantsRepository;
import uit.carbon_shop.repos.ChatsRepository;
import uit.carbon_shop.repos.ContractRepository;
import uit.carbon_shop.repos.MessageStatusRepository;
import uit.carbon_shop.repos.MessagesRepository;
import uit.carbon_shop.repos.OrderRepository;
import uit.carbon_shop.repos.OrderStatusRepository;
import uit.carbon_shop.repos.PaymentRepository;
import uit.carbon_shop.repos.ProjectRepository;
import uit.carbon_shop.repos.ReviewCompanyRepository;
import uit.carbon_shop.repos.ReviewProjectRepository;
import uit.carbon_shop.repos.StaffRepository;
import uit.carbon_shop.repos.UserRepository;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context with a
 * Datasource connected to the Testcontainers Docker instance. The instance is reused for all tests,
 * with all data wiped out before each test.
 */
@SpringBootTest(
        classes = CarbonShopApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("it")
@Sql("/data/clearAll.sql")
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    @ServiceConnection
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:17.0");

    static {
        postgreSQLContainer.withReuse(true)
                .start();
    }

    @LocalServerPort
    public int serverPort;

    @Autowired
    public ProjectRepository projectRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public OrderStatusRepository orderStatusRepository;

    @Autowired
    public PaymentRepository paymentRepository;

    @Autowired
    public ContractRepository contractRepository;

    @Autowired
    public ReviewCompanyRepository reviewCompanyRepository;

    @Autowired
    public ReviewProjectRepository reviewProjectRepository;

    @Autowired
    public StaffRepository staffRepository;

    @Autowired
    public ChatsRepository chatsRepository;

    @Autowired
    public MessagesRepository messagesRepository;

    @Autowired
    public ChatParticipantsRepository chatParticipantsRepository;

    @Autowired
    public MessageStatusRepository messageStatusRepository;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @SneakyThrows
    public String readResource(final String resourceName) {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
    }

}
