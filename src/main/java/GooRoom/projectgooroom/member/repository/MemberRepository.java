package GooRoom.projectgooroom.member.repository;

import GooRoom.projectgooroom.member.domain.LoginType;
import GooRoom.projectgooroom.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * MemberRepository
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * 이름으로 Member 검색
     * @param name
     * @return an {@link Optional} containing the {@link Member}
     * }
     */
    Optional<Member> findMemberByName(String name);

    /**
     * id로 Member 검색
     * @param id
     * @return {@link Optional} containing the {@link Member}
     */
    Optional<Member> findMemberById(Long id);

    /**
     * Email로 Member 검색
     * @param email
     * @return {@link Optional} containing the {@link Member}
     */
    Optional<Member> findMemberByEmail(String email);

    /**
     * nickname으로 Member 검색
     * @param nickname
     * @return
     */
    Optional<Member> findMemberByNickname(String nickname);

    /**
     * refreshToken으로 Member 검색
     * @param refreshToken
     * @return {@link Optional} containing the {@link Member}
     */
    Optional<Member> findMemberByRefreshToken(String refreshToken);

    /**
     * 로그인 타입과 소셜 ID로 Member 검색
     * @param loginType 이메일, OAuth2 유형 등 로그인타입
     * @param socialId 소셜로그인 시 소셜ID
     * @return {@link Optional} containing the {@link Member}
     */
    Optional<Member> findMemberByLoginTypeAndSocialId(LoginType loginType, String socialId);
}
