package bulletin_board.bulletin_board.repository;

import bulletin_board.bulletin_board.domain.Member;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    Member.MemberBuilder memberBuilder;

    @Autowired
    EntityManager em;
    @BeforeEach
    void before(){
        memberBuilder = Member.builder()
                .name("hyunwon")
                .nickname("gimguy")
                .id("khw7385")
                .password("1234")
                .date_of_birth(LocalDate.of(1999, 1, 27))
                .create_time(LocalDateTime.now().withNano(0));
    }

    @Test
    void 회원등록_및_조회(){
        Member member = memberBuilder.build();

        //save test
        Member saveMember = memberRepository.save(member);


        assertThat(member.getMember_id()).isEqualTo(saveMember.getMember_id());
        assertThat(member.getName()).isEqualTo(saveMember.getName());
        assertThat(member.getNickname()).isEqualTo(saveMember.getNickname());
        assertThat(member.getId()).isEqualTo(saveMember.getId());
        assertThat(member.getPassword()).isEqualTo(saveMember.getPassword());
        assertThat(member.getDate_of_birth()).isEqualTo(saveMember.getDate_of_birth());
        assertThat(member.getCreate_time()).isEqualTo(saveMember.getCreate_time());

        em.clear();

        //findById test
        Member findMember = memberRepository.findById(1).get();

        assertThat(member.getMember_id()).isEqualTo(findMember.getMember_id());
        assertThat(member.getName()).isEqualTo(findMember.getName());
        assertThat(member.getNickname()).isEqualTo(findMember.getNickname());
        assertThat(member.getId()).isEqualTo(findMember.getId());
        assertThat(member.getPassword()).isEqualTo(findMember.getPassword());
        assertThat(member.getDate_of_birth()).isEqualTo(findMember.getDate_of_birth());
        assertThat(member.getCreate_time()).isEqualTo(findMember.getCreate_time());


    }

}