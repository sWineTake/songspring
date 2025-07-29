package songspring.splearn.application.provided;

import songspring.splearn.domain.Member;

/**
 * 회원을 조회 한다
 */
public interface MemberFinder {

    Member find(Long memberId);

}
