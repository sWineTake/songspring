package songspring.splearn.application.member.provided;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;
import songspring.splearn.application.member.MemberModifyService;
import songspring.splearn.application.member.required.EmailSender;
import songspring.splearn.application.member.required.MemberRepository;
import songspring.splearn.domain.member.Profile;
import songspring.splearn.domain.shared.Email;
import songspring.splearn.domain.member.Member;
import songspring.splearn.domain.member.MemberFixture;
import songspring.splearn.domain.member.MemberStauts;

@DataJpaTest
class MemberRegisterManualTest {

    @Test
    void registerTestStub() {

        MemberRegister register = new MemberModifyService(
                new MemberRegisterManualTest.MemberRepositoryStub(),
                new MemberRegisterManualTest.EmailSenderStub(),
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

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }

        @Override
        public Optional<Member> findById(Long memberId) {
            return Optional.empty();
        }

        @Override
        public Optional<Member> findByProfile(Profile profile) {
            return Optional.empty();
        }

    }

    static class EmailSenderStub implements EmailSender {

        @Override
        public void send(Email email, String subject, String body) {

        }

    }

    @Test
    void registerTestMock() {
        MemberRegisterManualTest.EmailSenderMock emailSenderMock = new MemberRegisterManualTest.EmailSenderMock();
        MemberRegister register = new MemberModifyService(
                new MemberRegisterManualTest.MemberRepositoryMock(),
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

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }

        @Override
        public Optional<Member> findById(Long memberId) {
            return Optional.empty();
        }

        @Override
        public Optional<Member> findByProfile(Profile profile) {
            return Optional.empty();
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

        MemberRegister register = new MemberModifyService(
                new MemberRegisterManualTest.MemberRepositoryMock(),
                emailSenderMock,
                MemberFixture.createPasswordEncode()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStauts.PENDING);

        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());
    }
}
