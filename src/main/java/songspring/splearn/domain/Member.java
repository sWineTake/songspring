package songspring.splearn.domain;

import static org.springframework.util.Assert.state;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Member {
    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStauts status;

    private Member() {
    }

    public static Member create(MemberCreateRequest createRequest, PasswordEncode passwordEncode) {
        Member member = new Member();

        member.email = new Email(createRequest.email());
        member.nickname = Objects.requireNonNull(createRequest.nickname());
        member.passwordHash = Objects.requireNonNull(passwordEncode.encode(createRequest.password()));
        member.status = MemberStauts.PENDING;

        return member;
    }

    public void activate() {
        state(this.status == MemberStauts.PENDING, "PENDING 상태가 아닙니다");

        this.status = MemberStauts.ACTIVE;
    }

    public void deactivate() {
        state(status == MemberStauts.ACTIVE, "PENDING 상태가 아닙니다");

        this.status = MemberStauts.DEACTIVATED;
    }

    public boolean verifyPassword(String password, PasswordEncode passwordEncode) {
        return passwordEncode.matches(password, passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = Objects.requireNonNull(nickname);
    }

    public void changePassword(String newPassword, PasswordEncode passwordEncode) {

        this.passwordHash = passwordEncode.encode(Objects.requireNonNull(newPassword));

    }

    public boolean isActive() {
        return this.status == MemberStauts.ACTIVE;
    }
}
