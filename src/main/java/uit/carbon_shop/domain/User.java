package uit.carbon_shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class User extends BaseAccount {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", unique = true)
    private Company company;

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

}
