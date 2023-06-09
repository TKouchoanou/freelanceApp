package freelance.domain.core.objetValue;

import freelance.domain.common.exception.DomainException;
import org.apache.commons.validator.routines.IBANValidator;

public record Iban(String value) {

    public Iban(String value){
     IBANValidator ibanValidator= new IBANValidator(new IBANValidator.Validator[]{new IBANValidator.Validator("FR", 27, "FR\\d{12}[A-Z0-9]{11}\\d{2}")});
     if(ibanValidator.isValid(value)){
         this.value=value;
     }else {
         throw  new DomainException("you provide invalid iban "+value);
     }
    }

}
