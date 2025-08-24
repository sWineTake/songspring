package songspring.splearn.domain.member;

public interface PasswordEncode {

    String encode(String password);

    boolean matches(String password, String passwordHash);

}
