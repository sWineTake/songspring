package songspring.splearn.domain;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Getter
    private Long id;
}
