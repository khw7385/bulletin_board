package bulletin_board.bulletin_board.repository;

import bulletin_board.bulletin_board.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    Member member1;
    Member member2;

    @Autowired
    EntityManager em;
    @BeforeEach
    void before(){
        member1 = Member.builder()
                .name("hyunwon")
                .nickname("gimguy")
                .login_id("khw7385")
                .password("1234")
                .date_of_birth(LocalDate.of(1999, 1, 27))
                .create_time(LocalDateTime.now().withNano(0)).build();

        member2 = Member.builder()
                .name("ziho")
                .nickname("zico")
                .login_id("zico1234")
                .password("12345")
                .date_of_birth(LocalDate.of(1992, 9, 14))
                .create_time(LocalDateTime.now().withNano(0)).build();
    }

    @Test
    void 회원등록_및_조회(){

        //save test
        Member saveMember = memberRepository.save(member1);


        assertThat(member1.getMember_id()).isEqualTo(saveMember.getMember_id());
        assertThat(member1.getName()).isEqualTo(saveMember.getName());
        assertThat(member1.getNickname()).isEqualTo(saveMember.getNickname());
        assertThat(member1.getLogin_id()).isEqualTo(saveMember.getLogin_id());
        assertThat(member1.getPassword()).isEqualTo(saveMember.getPassword());
        assertThat(member1.getDate_of_birth()).isEqualTo(saveMember.getDate_of_birth());
        assertThat(member1.getCreate_time()).isEqualTo(saveMember.getCreate_time());

        em.clear();

        //findById test
        Member findMember = memberRepository.findById(1).get();

        assertThat(member1.getMember_id()).isEqualTo(findMember.getMember_id());
        assertThat(member1.getName()).isEqualTo(findMember.getName());
        assertThat(member1.getNickname()).isEqualTo(findMember.getNickname());
        assertThat(member1.getLogin_id()).isEqualTo(findMember.getLogin_id());
        assertThat(member1.getPassword()).isEqualTo(findMember.getPassword());
        assertThat(member1.getDate_of_birth()).isEqualTo(findMember.getDate_of_birth());
        assertThat(member1.getCreate_time()).isEqualTo(findMember.getCreate_time());


    }
    @Test
    void 회원_아이디_혹은_이름으로_조회(){
        Member saveMember1 = memberRepository.save(member1);
        Member saveMember2 = memberRepository.save(member2);

        List<Member> findMembers = memberRepository.findByLogin_idOrNickname("khw7385", "zico");

        assertThat(saveMember1.getMember_id()).isEqualTo(findMembers.get(0).getMember_id());
        assertThat(saveMember2.getMember_id()).isEqualTo(findMembers.get(1).getMember_id());

    }
}