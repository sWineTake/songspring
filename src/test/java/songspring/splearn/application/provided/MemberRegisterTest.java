package songspring.splearn.application.provided;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.validation.annotation.Validated;
import songspring.splearn.SplearnTestConfig;
import songspring.splearn.domain.DuplicateEmailException;
import songspring.splearn.domain.Member;
import songspring.splearn.domain.MemberFixture;
import songspring.splearn.domain.MemberRegisterRequest;
import songspring.splearn.domain.MemberStauts;

@Transactional
@SpringBootTest
@Import(SplearnTestConfig.class)
// @TestConstructor(autowireMode = AutowireMode.ALL) -> junit-platform.properties 설정으로 변경
public record MemberRegisterTest(MemberRegister memberRegister) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStauts.PENDING);

    }

    @Test
    void 이메일_중복() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() ->
                memberRegister.register(MemberFixture.createMemberRegisterRequest())
        ).isInstanceOf(DuplicateEmailException.class);

    }

    @Test
    void 회원_가입_REQ_VALIDATION() {

        MemberRegisterRequest member1 = new MemberRegisterRequest("user@naver.com", "user", "password1");
        assertThatThrownBy(() -> memberRegister.register(member1)).isInstanceOf(ConstraintViolationException.class);

        MemberRegisterRequest member2 = new MemberRegisterRequest("user@naver.com", "user1", "pass");
        assertThatThrownBy(() -> memberRegister.register(member2)).isInstanceOf(ConstraintViolationException.class);

        MemberRegisterRequest member3 = new MemberRegisterRequest("user@naver.com", "user11111111111111111111111", "pass");
        assertThatThrownBy(() -> memberRegister.register(member3)).isInstanceOf(ConstraintViolationException.class);

        MemberRegisterRequest member4 = new MemberRegisterRequest("usernaver.com", "user11111111111111111111111", "pass");
        assertThatThrownBy(() -> memberRegister.register(member4)).isInstanceOf(ConstraintViolationException.class);
    }

}
