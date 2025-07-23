package songspring.splearn.application.required;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static songspring.splearn.domain.MemberFixture.createMember;
import static songspring.splearn.domain.MemberFixture.createPasswordEncode;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;
import songspring.splearn.application.MemberService;
import songspring.splearn.application.provided.MemberRegister;
import songspring.splearn.domain.Email;
import songspring.splearn.domain.Member;
import songspring.splearn.domain.MemberFixture;
import songspring.splearn.domain.MemberStauts;

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

    @Test
    void 이메일_중복_확인() {
        Member member = createMember(createPasswordEncode());

        memberRepository.save(member);

        Member duplicateMember = createMember(createPasswordEncode());
        assertThatThrownBy(() -> memberRepository.save(duplicateMember)).isInstanceOf(DataIntegrityViolationException.class);


    }

    @Test
    void registerTestStub() {

        MemberRegister register = new MemberService(
                new MemberRepositoryStub(),
                new EmailSenderStub(),
                MemberFixture.createPasswordEncode()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStauts.PENDING);

    }

    static class MemberRepositoryStub implements MemberRepository {

        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member,"id", 1L);
            return member;
        }

    }

    static class EmailSenderStub implements EmailSender {

        @Override
        public void send(Email email, String subject, String body) {

        }

    }

    @Test
    void registerTestMock() {
        EmailSenderMock emailSenderMock = new EmailSenderMock();
        MemberRegister register = new MemberService(
                new MemberRepositoryMock(),
                emailSenderMock,
                MemberFixture.createPasswordEncode()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStauts.PENDING);

        assertThat(emailSenderMock.tos).hasSize(1);
        assertThat(emailSenderMock.tos.getFirst()).isEqualTo(member.getEmail());
    }

    static class MemberRepositoryMock implements MemberRepository {
        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member,"id", 1L);
            return member;
        }
    }

    static class EmailSenderMock implements EmailSender {
        List<Email> tos = new ArrayList<>();

        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }

    }

    @Test
    void registerTestMockito() {
        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);

        MemberRegister register = new MemberService(
                new MemberRepositoryMock(),
                emailSenderMock,
                MemberFixture.createPasswordEncode()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStauts.PENDING);

        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());
    }
}
