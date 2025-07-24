package songspring.splearn;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import songspring.splearn.application.required.EmailSender;
import songspring.splearn.domain.MemberFixture;
import songspring.splearn.domain.PasswordEncode;

@TestConfiguration
public class SplearnTestConfig {

    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println("sending email : " + email);
    }

    @Bean
    public PasswordEncode passwordEncode() {
        return MemberFixture.createPasswordEncode();
    }

}
