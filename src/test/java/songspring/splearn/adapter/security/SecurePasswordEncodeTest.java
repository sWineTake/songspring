package songspring.splearn.adapter.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SecurePasswordEncodeTest {

    @Test
    void 패스워드_인증() {
        SecurePasswordEncode securePasswordEncode = new SecurePasswordEncode();

        String security = securePasswordEncode.encode("security");

        assertThat(securePasswordEncode.matches("security", security)).isTrue();
        assertThat(securePasswordEncode.matches("wrong", security)).isFalse();

    }

}
