package freelance.domain.output.notifications.email;

import java.util.List;

public interface EmailService<T extends EmailService.Email, A extends EmailService.Attachment> {

    void sendEmail(T email) throws EmailNotificationException;

    interface Email {
        String getFrom();

        String getTo();

        String getSubject();

        String getBody();

        List<? extends Attachment> getAttachments();

        List<String> getCcList();

        List<String> getBccList();

        boolean isHtml();
    }

    interface Attachment {
        String getName();

        byte[] getContent();

        String getMimeType();
    }
}
