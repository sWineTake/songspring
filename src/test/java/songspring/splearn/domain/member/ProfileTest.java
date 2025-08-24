package songspring.splearn.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProfileTest {

    @Test
    void profile() {

        new Profile("woosong");

    }

    @Test
    void profile_fail() {

        assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("adsf890f809fdsafd8as90fads")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("ASDX")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("한글프로필은안됨")).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void url() {

        var profile = new Profile("woosong");

        assertThat(profile.url()).isEqualTo("@" + profile.address());

    }

}
