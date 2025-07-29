package songspring.splearn.application.provided;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import songspring.splearn.SplearnTestConfig;
import songspring.splearn.domain.Member;
import songspring.splearn.domain.MemberFixture;

@Transactional
@SpringBootTest
@Import(SplearnTestConfig.class)
// @TestConstructor(autowireMode = AutowireMode.ALL) -> junit-platform.properties 설정으로 변경
record MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister, EntityManager em) {

    @Test
    void find() {

        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        em.flush();
        em.clear();

        Member found = memberFinder.find(member.getId());

        assertThat(found.getId()).isEqualTo(member.getId());

    }

}
