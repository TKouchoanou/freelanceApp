package freelance.domain.models.objetValue;

import freelance.domain.exception.DomainException;
public record CleRib(String value) {
    public static int cleRibNumberOfDigit=2;
    public CleRib(String value) {
        if (isNumeric(value) && value.length() == cleRibNumberOfDigit) {
            this.value = value.toUpperCase();
        } else {
            throw new DomainException("you provide invalid cle value ");
        }
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
