package songspring.splearn.application.required;

import static org.assertj.core.api.Assertions.assertThat;
import static songspring.splearn.domain.MemberFixture.createMember;
import static songspring.splearn.domain.MemberFixture.createPasswordEncode;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import songspring.splearn.domain.Member;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    void 회원을_저장_할_수있다() {
        Member member = createMember(createPasswordEncode());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        em.flush();

    }



}
