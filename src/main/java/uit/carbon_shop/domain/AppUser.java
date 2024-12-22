package uit.carbon_shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uit.carbon_shop.model.UserRole;


@Entity
@Table(name = "AppUsers")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class AppUser {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long userId;

    @Column(nullable = false)
    private String password;

    @Column
    private String resetPasswordUid;

    @Column
    private OffsetDateTime resetPasswordStart;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    private LocalDateTime approvedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", unique = true)
    private Company company;

    @OneToMany(mappedBy = "auditBy")
    private Set<Project> auditedProjects;

    @OneToMany(mappedBy = "createdBy")
    private Set<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "FavoriteProjectses",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "projectId")
    )
    private Set<Project> favoriteProjects;

    @OneToMany(mappedBy = "reviewBy")
    private Set<CompanyReview> companyReviews;

    @OneToMany(mappedBy = "reviewBy")
    private Set<ProjectReview> projectReviews;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

}
