package freelance.domain.models.objetValue;

import freelance.domain.exception.DomainException;

public record Bic (String value) {
   public static int BIC8_LENGTH=8;
    public static int BIC11_LENGTH=11;
    public Bic(String value){
        if(notContainNumericChar(value) && (value.length()==BIC8_LENGTH || value.length()==BIC11_LENGTH)){
            this.value=value.toUpperCase();
        }else {
            throw  new DomainException("you provide invalid bic ");
        }
    }
    private boolean notContainNumericChar(String value) {
        for (char c : value.toCharArray()) {
            if(isNumeric(String.valueOf(c)))
                return false;
        }
       return true;
    }

    private  boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
             Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
