package uit.carbon_shop.config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import uit.carbon_shop.CarbonShopApplication;
import uit.carbon_shop.repos.AppUserRepository;
import uit.carbon_shop.repos.CompanyRepository;
import uit.carbon_shop.repos.CompanyReviewRepository;
import uit.carbon_shop.repos.FileDocumentRepository;
import uit.carbon_shop.repos.OrderRepository;
import uit.carbon_shop.repos.ProjectRepository;
import uit.carbon_shop.repos.ProjectReviewRepository;


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
@Sql({"/data/clearAll.sql", "/data/appUserData.sql"})
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    @ServiceConnection
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:17.2");
    private static final GenericContainer<?> mailpitContainer = new GenericContainer<>("axllent/mailpit:v1.21");
    public static String smtpHost;
    public static Integer smtpPort;
    public static String messagesUrl;

    static {
        postgreSQLContainer.withReuse(true)
                .start();
        mailpitContainer.withExposedPorts(1025, 8025)
                .waitingFor(Wait.forLogMessage(".*accessible via.*", 1))
                .withReuse(true)
                .start();
        smtpHost = mailpitContainer.getHost();
        smtpPort = mailpitContainer.getMappedPort(1025);
        messagesUrl = "http://" + smtpHost + ":" + mailpitContainer.getMappedPort(8025) + "/api/v1/messages";
    }

    @LocalServerPort
    public int serverPort;

    @Autowired
    public ProjectRepository projectRepository;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public CompanyRepository companyRepository;

    @Autowired
    public AppUserRepository appUserRepository;

    @Autowired
    public FileDocumentRepository fileDocumentRepository;

    @Autowired
    public CompanyReviewRepository companyReviewRepository;

    @Autowired
    public ProjectReviewRepository projectReviewRepository;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @DynamicPropertySource
    public static void setDynamicProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", () -> smtpHost);
        registry.add("spring.mail.port", () -> smtpPort);
        registry.add("spring.mail.properties.mail.smtp.auth", () -> false);
        registry.add("spring.mail.properties.mail.smtp.starttls.enable", () -> false);
        registry.add("spring.mail.properties.mail.smtp.starttls.required", () -> false);
    }

    @BeforeEach
    public void beforeEach() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete(messagesUrl);
    }

    @SneakyThrows
    public String readResource(final String resourceName) {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public void waitForMessages(final int total) {
        int loop = 0;
        while (loop++ < 25) {
            final Response messagesResponse = RestAssured
                    .given()
                        .accept(ContentType.JSON)
                    .when()
                        .get(messagesUrl);
            if (messagesResponse.jsonPath().getInt("total") == total) {
                return;
            }
            Thread.sleep(250);
        }
        throw new RuntimeException("Could not find " + total + " messages in time.");
    }

    public String sellerOrBuyerUserToken() {
        // user sellerOrBuyer@invalid.bootify.io, expires 2040-01-01
        return "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiJzZWxsZXJPckJ1eWVyQGludmFsaWQuYm9vdGlmeS5pbyIsInJvbGVzIjpbIlNFTExFUl9PUl9CVVlFUiJdLCJpc3MiOiJib290aWZ5IiwiaWF0IjoxNzMwMDEyOTQzLCJleHAiOjIyMDg5ODg4MDB9." +
                "SWaqt9_3DcPr3_aXUiw_RxZDkz_DRa4Vbtv6DYge-juDSe89THJ3cpdJC8XJu-fiTbh10nDGeDY5HgAiokdmjA";
    }

    public String mediatorUserToken() {
        // user mediator@invalid.bootify.io, expires 2040-01-01
        return "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiJtZWRpYXRvckBpbnZhbGlkLmJvb3RpZnkuaW8iLCJyb2xlcyI6WyJNRURJQVRPUiJdLCJpc3MiOiJib290aWZ5IiwiaWF0IjoxNzMwMDEyOTQzLCJleHAiOjIyMDg5ODg4MDB9." +
                "KCE6MSWDj_vIu5W6g6tx7jZXh5knBtvGNUi4QRz5HFzONKgazWATvKiNCjuixY4vBNKoJdQBnD8ktmaPEBEgqg";
    }

}
