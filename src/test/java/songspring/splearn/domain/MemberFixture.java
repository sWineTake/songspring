package songspring.splearn.domain;

public class MemberFixture {

    public static Member createMember(PasswordEncode passwordEncode) {
        return Member.register(new MemberRegisterRequest("user@naver.com", "User1", "password123"), passwordEncode);
    }

    public static Member createMember(String email, PasswordEncode passwordEncode) {
        return Member.register(new MemberRegisterRequest(email, "user1", "password1"), passwordEncode);
    }

    public static PasswordEncode createPasswordEncode() {
        return new PasswordEncode() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }

}
