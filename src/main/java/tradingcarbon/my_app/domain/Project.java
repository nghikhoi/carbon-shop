package tradingcarbon.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Project {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID projectId;

    @Column
    private String projectName;

    @Column
    private String projectAddress;

    @Column
    private String projectSize;

    @Column
    private String projectTimeStart;

    @Column
    private String projectTimeEnd;

    @Column
    private String projectRangeCarbon;

    @Column
    private String organizationProvide;

    @Column
    private String numberCarBonCredit;

    @Column
    private String creditTimeStart;

    @Column
    private String price;

    @Column
    private String methodPayment;

    @Column
    private String projectCredit;

    @Column
    private String creditDetail;

    @Column
    private String creditId;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_id", nullable = false)
    private User userId;

    @OneToMany(mappedBy = "projectId")
    private Set<OrderStatus> orderStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_project_id_id")
    private ReviewProject reviewProjectId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
