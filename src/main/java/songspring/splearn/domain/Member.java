package songspring.splearn.domain;

import static org.springframework.util.Assert.state;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Member {
    private String email;

    private String nickname;

    private String passwordHash;

    private MemberStauts status;

    public static Member of(String email, String nickname, String passwordHash) {
        Member member = new Member();
        member.email = Objects.requireNonNull(email);
        member.nickname = Objects.requireNonNull(nickname);
        member.passwordHash = Objects.requireNonNull(passwordHash);
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

}
