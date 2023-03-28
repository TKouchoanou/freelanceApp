package freelance.application.query.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@Builder
public class Freelance {
    //imagine un tableau de bord comme dans git ou il y a les users, employee et freeLance dans chaque partie
    // si un utilisateur est freelance il apparait dans la partie freelance et ainsi pour l'employee
    //dans le tableau utilisateur , on peut filter les employeurs , les freelances , des users sans profile et faiire des recherches avec filtre sur ces critères
    //si un user est employee et freelance il se retrouvera des trois côtés . J'imagine un emoticone visuel pour permettre de différencier
    Long  id;
    User user;
    Rib rib;
    Company company;
    Set<BillingSummary> billing;
}
