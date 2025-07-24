package songspring.splearn.application.provided;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import songspring.splearn.SplearnTestConfig;
import songspring.splearn.domain.DuplicateEmailException;
import songspring.splearn.domain.Member;
import songspring.splearn.domain.MemberFixture;
import songspring.splearn.domain.MemberStauts;

@SpringBootTest
@Transactional
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

}
