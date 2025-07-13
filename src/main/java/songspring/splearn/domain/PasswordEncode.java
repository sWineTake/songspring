package songspring.splearn.domain;

public interface PasswordEncode {

    String encode(String password);

    boolean matches(String password, String passwordHash);

}
