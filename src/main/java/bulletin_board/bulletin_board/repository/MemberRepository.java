package bulletin_board.bulletin_board.repository;

import bulletin_board.bulletin_board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    @Query("select m From Member m where m.login_id = :login_id or m.nickname = :nickname")
    List<Member> findByLogin_idOrNickname(@Param("login_id")String login_id, @Param("nickname") String nickname);

    @Query("select m From Member m where m.login_id = :login_id")
    Optional<Member> findByLogin_id(@Param("login_id") String id);
    @Query("select m From Member m where m.nickname = :nickname")
    Optional<Member> findByNickname(@Param("nickname") String nickname);
}
