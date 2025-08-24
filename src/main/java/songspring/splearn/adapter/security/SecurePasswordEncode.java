package songspring.splearn.adapter.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import songspring.splearn.domain.member.PasswordEncode;

@Component
public class SecurePasswordEncode implements PasswordEncode {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public boolean matches(String password, String passwordHash) {
        return bCryptPasswordEncoder.matches(password, passwordHash);
    }
}
