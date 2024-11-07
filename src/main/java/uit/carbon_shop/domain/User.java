package uit.carbon_shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "\"User\"")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID userId;

    @Column
    private String companyName;

    @Column
    private String companyEmail;

    @Column
    private String companyAddress;

    @Column
    private String companyTaxCode;

    @Column
    private String password;

    @Column
    private String companySector;

    @Column
    private String companyAgent;

    @Column
    private String companyAgentPhoneNumber;

    @OneToMany(mappedBy = "userId")
    private Set<Project> projectId;

    @OneToMany(mappedBy = "sellerId")
    private Set<Order> orderSellerId;

    @OneToMany(mappedBy = "buyerId")
    private Set<Order> orderBuyerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_company_id_id")
    private ReviewCompany reviewCompanyId;

    @ManyToMany(mappedBy = "userId")
    private Set<Chats> chatId;

    @OneToMany(mappedBy = "senderId")
    private Set<Messages> messagesId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
