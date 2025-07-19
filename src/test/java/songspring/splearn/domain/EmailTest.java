package songspring.splearn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void 이메일_검증() {

        Email email1 = new Email("user@naver.com");
        Email email2 = new Email("user@naver.com");

        assertThat(email1).isEqualTo(email2);

    }

}
