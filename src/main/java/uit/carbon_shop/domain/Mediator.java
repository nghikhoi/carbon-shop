package uit.carbon_shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Mediators")
@Getter
@Setter
public class Mediator extends BaseUser {

    @OneToMany(mappedBy = "auditBy")
    private Set<Project> auditedProjects;

}
