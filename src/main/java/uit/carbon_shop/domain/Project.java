package uit.carbon_shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uit.carbon_shop.model.ProjectStatus;


@Entity
@Table(name = "Projects")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Project {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID projectId;

    @Column(columnDefinition = "text")
    private String name;

    @Column(columnDefinition = "text")
    private String address;

    @Column
    private String size;

    @Column
    private LocalDateTime timeStart;

    @Column
    private LocalDateTime timeEnd;

    @Column
    private String produceCarbonRate;

    @Column
    private String partner;

    @Column
    private String auditByOrg;

    @Column
    private Long creditAmount;

    @Column
    private String cert;

    @Column
    private String price;

    @Column
    private String methodPayment;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Long> projectImages;

    @Column
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_company_id")
    private Company ownerCompany;

    @OneToMany(mappedBy = "project")
    private Set<Order> orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_by_id")
    private Mediator auditBy;

    @OneToMany(mappedBy = "project")
    private Set<ProjectReview> projectReviews;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

}
