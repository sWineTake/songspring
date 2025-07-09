package songspring.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void createMember() {
        var member = Member.of("user@naver.com", "User1", "password123");

        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStauts.PENDING);
    }

}
