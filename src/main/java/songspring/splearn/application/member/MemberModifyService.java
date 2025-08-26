package songspring.splearn.application.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import songspring.splearn.application.member.provided.MemberRegister;
import songspring.splearn.application.member.required.EmailSender;
import songspring.splearn.application.member.required.MemberRepository;
import songspring.splearn.domain.member.DuplicateEmailException;
import songspring.splearn.domain.member.MemberInfoUpdateRequest;
import songspring.splearn.domain.shared.Email;
import songspring.splearn.domain.member.Member;
import songspring.splearn.domain.member.MemberRegisterRequest;
import songspring.splearn.domain.member.PasswordEncode;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

    // private final MemberFinder memberFinder;
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

    @Override
    public Member activate(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id : " + memberId));

        member.activate();

        return memberRepository.save(member);
    }

    @Override
    public Member deactivate(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id : " + memberId));

        member.deactivate();

        return memberRepository.save(member);
    }

    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest memberInfoUpdateRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id : " + memberId));

        member.updateInfo(memberInfoUpdateRequest);

        return memberRepository.save(member);
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
