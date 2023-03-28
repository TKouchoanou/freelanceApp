package freelance.domain.output.notifications.email;

import freelance.domain.common.exception.DomainException;

public class EmailNotificationException extends DomainException {
    public EmailNotificationException(String message, boolean messageI18nKey) {
        super(message, messageI18nKey);
    }

    public EmailNotificationException(String message) {
        super(message);
    }
}
