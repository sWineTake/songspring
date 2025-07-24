package songspring.splearn.adapter.integration;

import org.springframework.stereotype.Component;
import songspring.splearn.application.required.EmailSender;
import songspring.splearn.domain.Email;

@Component
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender : " + email);
    }

}
