package freelance.service.query.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class Company {
    Long id;
    String name;
    Long ribId;

}
