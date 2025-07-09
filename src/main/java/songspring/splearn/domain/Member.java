package songspring.splearn.domain;


public class Member {
    private String email;
    private String nickname;
    private String passwordHash;
    private MemberStauts status;

    public static Member of(String email, String nickname, String passwordHash) {
        Member member = new Member();
        member.email = email;
        member.nickname = nickname;
        member.passwordHash = passwordHash;
        member.status = MemberStauts.PENDING;
        return member;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public MemberStauts getStatus() {
        return status;
    }
}
