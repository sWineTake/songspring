package songspring.splearn.domain.member;

import static org.springframework.util.Assert.state;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import songspring.splearn.domain.AbstractEntity;
import songspring.splearn.domain.shared.Email;

@Entity
@Getter
@ToString(callSuper = true, exclude = "detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
public class Member extends AbstractEntity {

    @NaturalId
    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStauts status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberDetail detail;

    public static Member register(MemberRegisterRequest createRequest, PasswordEncode passwordEncode) {
        Member member = new Member();

        member.email = new Email(createRequest.email());
        member.nickname = Objects.requireNonNull(createRequest.nickname());
        member.passwordHash = Objects.requireNonNull(passwordEncode.encode(createRequest.password()));
        member.status = MemberStauts.PENDING;

        member.detail = MemberDetail.create();

        return member;
    }

    public void activate() {
        state(this.status == MemberStauts.PENDING, "PENDING 상태가 아닙니다");

        this.status = MemberStauts.ACTIVE;
        this.detail.updateActivatedAt();
    }

    public void deactivate() {
        state(status == MemberStauts.ACTIVE, "PENDING 상태가 아닙니다");

        this.status = MemberStauts.DEACTIVATED;
        this.detail.updateDeactivatedAt();
    }

    public boolean verifyPassword(String password, PasswordEncode passwordEncode) {
        return passwordEncode.matches(password, passwordHash);
    }


    public void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.nickname = Objects.requireNonNull(updateRequest.nickname());

        this.detail.updateInfo(updateRequest);
    }

    public void changePassword(String newPassword, PasswordEncode passwordEncode) {

        this.passwordHash = passwordEncode.encode(Objects.requireNonNull(newPassword));

    }

    public boolean isActive() {
        return this.status == MemberStauts.ACTIVE;
    }
}
