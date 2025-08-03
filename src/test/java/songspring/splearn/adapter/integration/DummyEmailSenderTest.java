package songspring.splearn.adapter.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import songspring.splearn.domain.Email;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void 더미_이메일_전송(StdOut out) {

        DummyEmailSender dummyEmailSender = new DummyEmailSender();
        dummyEmailSender.send(new Email("twocowsong@naver.com"), "subject", "body");

        assertThat(out.capturedLines()[0]).isEqualTo("DummyEmailSender : Email[address=twocowsong@naver.com]");


    }


}
