package freelance.domain.common.exception;

import java.io.Serial;

public class DomainException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 117400658952600303L;

    private final boolean messageI18nKey;

    public DomainException(String message, boolean messageI18nKey) {
        super(message);
        this.messageI18nKey = messageI18nKey;
    }

    public DomainException(String message) {
        this(message, true);
    }

    public boolean isMessageI18nKey(){
        return this.messageI18nKey;
    }
}
