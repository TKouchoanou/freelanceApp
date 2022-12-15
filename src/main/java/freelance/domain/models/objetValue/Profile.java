package freelance.domain.models.objetValue;

import freelance.domain.exception.DomainException;

import java.util.Arrays;

public enum Profile {
    FREELANCE(0),SALARY(1);
    private final Integer value;
    Profile(Integer value){
        this.value=value;
    }
    public static Profile fromValue(Integer value){
      return  Arrays.stream(Profile.values()).filter(v->v.value.equals(value)).findFirst().orElseThrow(()->new DomainException(" no profil with value "+value));
    }
}
