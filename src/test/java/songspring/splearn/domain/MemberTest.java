package songspring.splearn.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void createMember() {
        var member = Member.of("user@naver.com", "User1", "password123");

        assertThat(member.getStatus()).isEqualTo(MemberStauts.PENDING);
    }

    @Test
    void 회원_생성자에서_필수값_NULL체크를_하는가() {

        assertThatThrownBy(() -> Member.of(null, "User1", "password"))
                .isInstanceOf(NullPointerException.class);

    }

    @Test
    void activate() {

        var member = Member.of("user@naver.com", "User1", "password123");

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStauts.ACTIVE);

    }

    @Test
    void 활성상태_변경_실패() {

        var member = Member.of("user@naver.com", "User1", "password123");

        member.activate();

        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);

    }

    @Test
    void 비활성화_상태로_변경할수_있다() {

        var member = Member.of("user@naver.com", "User1", "password123");
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStauts.DEACTIVATED);

    }

    @Test
    void 비활성_상태에서는_또_비활성_상태로_변경할_수_없다(){

        var member = Member.of("user@naver.com", "User1", "password123");

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

    }



}
