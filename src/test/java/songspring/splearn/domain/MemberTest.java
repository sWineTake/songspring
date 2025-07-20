package songspring.splearn.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

    Member member;
    PasswordEncode passwordEncode;

    @BeforeEach
    void setUp() {

        this.passwordEncode = new PasswordEncode() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };

        MemberRegisterRequest createRequest = new MemberRegisterRequest("user@naver.com", "User1", "password123");

        member = Member.register(createRequest, passwordEncode);
    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStauts.PENDING);
    }

    @Test
    void activate() {

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStauts.ACTIVE);

    }

    @Test
    void 활성상태_변경_실패() {

        member.activate();

        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);

    }

    @Test
    void 비활성화_상태로_변경할수_있다() {

        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStauts.DEACTIVATED);

    }

    @Test
    void 비활성_상태에서는_또_비활성_상태로_변경할_수_없다(){

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

    }


    @Test
    void 패스워드를_검증한다() {

        assertThat(member.verifyPassword("password123", passwordEncode)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncode)).isFalse();

    }

    @Test
    void NICK_NAME을_변경할수_있다() {

        String newNickname = "user1000";

        member.changeNickname(newNickname);

        assertThat(member.getNickname()).isEqualTo(newNickname);

    }

    @Test
    void PASSWORD를_변경_할_수_있다() {

        String newPassword = "newPassword";

        member.changePassword(newPassword, passwordEncode);

        assertThat(member.verifyPassword(newPassword, passwordEncode)).isTrue();

    }

    @Test
    void 활성상태인가() {

        assertThat(member.isActive()).isFalse();

        member.activate();

        assertThat(member.isActive()).isTrue();

        member.deactivate();

        assertThat(member.isActive()).isFalse();

    }

    @Test
    void 이메일_검증() {

        assertThatThrownBy(() ->
                Member.register(new MemberRegisterRequest("sdkfljadsl", "user1", "password1"), passwordEncode)
        ).isInstanceOf(IllegalArgumentException.class);

        // Member.create(new MemberRegisterRequest("sdkfljadsl", "user1", "password1"), passwordEncode);

    }
}
