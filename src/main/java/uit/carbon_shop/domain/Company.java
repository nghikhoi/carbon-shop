package uit.carbon_shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
import uit.carbon_shop.model.CompanyStatus;


@Entity
@Table(name = "Companies")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Company {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String taxCode;

    @Column
    private String email;

    @Column
    private String industry;

    @Column
    @Enumerated(EnumType.STRING)
    private CompanyStatus status;

    @OneToOne(mappedBy = "company", fetch = FetchType.LAZY)
    private User representative;

    @OneToMany(mappedBy = "ownerCompany")
    private Set<Project> projects;

    @OneToMany(mappedBy = "company")
    private Set<CompanyReview> comparnyReviews;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

}
