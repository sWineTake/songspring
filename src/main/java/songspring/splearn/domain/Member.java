package songspring.splearn.domain;

import static org.springframework.util.Assert.state;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

@Entity
@Getter
@ToString
@NaturalIdCache
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @NaturalId
    private Email email;

    private String nickname;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private MemberStauts status;

    public static Member register(MemberRegisterRequest createRequest, PasswordEncode passwordEncode) {
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
