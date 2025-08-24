package songspring.splearn.domain.member;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static songspring.splearn.domain.member.MemberFixture.createMember;
import static songspring.splearn.domain.member.MemberFixture.createPasswordEncode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

    Member member;
    PasswordEncode passwordEncode;

    @BeforeEach
    void setUp() {

        this.passwordEncode = createPasswordEncode();

        member = createMember(passwordEncode);
    }

    @Test
    void registerMember() {

        assertThat(member.getStatus()).isEqualTo(MemberStauts.PENDING);

        assertThat(member.getDetail().getRegisteredAt()).isNotNull();

    }

    @Test
    void activate() {

        assertThat(member.getDetail().getActivatedAt()).isNull();

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStauts.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();

    }

    @Test
    void 활성상태_변경_실패() {

        member.activate();

        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);

    }

    @Test
    void 비활성화_상태로_변경할수_있다() {

        assertThat(member.getDetail().getActivatedAt()).isNull();
        assertThat(member.getDetail().getDeactivatedAt()).isNull();

        member.activate();
        member.deactivate();

        assertThat(member.getDetail().getActivatedAt()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStauts.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
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

        assertThatThrownBy(() -> createMember("jaldksfjas", passwordEncode)).isInstanceOf(IllegalArgumentException.class);
        // Member.create(new MemberRegisterRequest("sdkfljadsl", "user1", "password1"), passwordEncode);

    }

    @Test
    void updateInfo() {
        member.activate();

        var req = new MemberInfoUpdateRequest("woosong", "newprofile1234", "자기소개");

        member.updateInfo(req);

        assertThat(member.getNickname()).isEqualTo(req.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(req.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(req.introduction());

    }
}
