package freelance.service.query.model;

import freelance.domain.models.objetValue.Bic;
import freelance.domain.models.objetValue.CleRib;
import freelance.domain.models.objetValue.Iban;
import freelance.domain.models.objetValue.RibId;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Rib {
    Long id;
    String username;
    String iban ;
    String bic;
    String cleRib;
}
