package songspring.splearn.domain.member;

public record MemberInfoUpdateRequest(
    String nickname,
    String profileAddress,
    String introduction
) {

}
