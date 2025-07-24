package songspring.splearn.application.required;

import java.util.Optional;
import org.springframework.data.repository.Repository;
import songspring.splearn.domain.Email;
import songspring.splearn.domain.Member;

/**
 * 회원 정보를 저장하거나 조회한다.
 */
public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    Optional<Member> findByEmail(Email email);


}
