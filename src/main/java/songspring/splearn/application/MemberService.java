package songspring.splearn.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import songspring.splearn.application.provided.MemberRegister;
import songspring.splearn.application.required.EmailSender;
import songspring.splearn.application.required.MemberRepository;
import songspring.splearn.domain.DuplicateEmailException;
import songspring.splearn.domain.Email;
import songspring.splearn.domain.Member;
import songspring.splearn.domain.MemberRegisterRequest;
import songspring.splearn.domain.PasswordEncode;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncode passwordEncode;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {

        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncode);
        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일 입니다.");
        }
    }

}
