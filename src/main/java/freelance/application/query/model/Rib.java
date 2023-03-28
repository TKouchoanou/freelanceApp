package freelance.application.query.model;

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
