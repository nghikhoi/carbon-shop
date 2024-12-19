package uit.carbon_shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Users")
@Getter
@Setter
public class User extends BaseUser {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", unique = true)
    private Company company;

    @OneToMany(mappedBy = "createdBy")
    private Set<Order> orders;

}
