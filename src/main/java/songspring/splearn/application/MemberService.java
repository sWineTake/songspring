package songspring.splearn.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import songspring.splearn.application.provided.MemberRegister;
import songspring.splearn.application.required.EmailSender;
import songspring.splearn.application.required.MemberRepository;
import songspring.splearn.domain.Member;
import songspring.splearn.domain.MemberRegisterRequest;
import songspring.splearn.domain.PasswordEncode;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncode passwordEncode;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {

        Member member = Member.register(registerRequest, passwordEncode);
        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.");

        return member;
    }

}
