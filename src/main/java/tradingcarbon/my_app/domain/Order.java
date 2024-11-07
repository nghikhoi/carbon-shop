package tradingcarbon.my_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "\"Order\"")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Order {

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
    private Long orderId;

    @Column
    private String numberCredits;

    @Column
    private String price;

    @Column
    private String total;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id_id", unique = true)
    private OrderStatus orderStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id_id")
    private User sellerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id_id")
    private User buyerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id_id", unique = true)
    private Payment paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constract_id_id", unique = true)
    private Contract constractId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id_id")
    private Staff staffId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
