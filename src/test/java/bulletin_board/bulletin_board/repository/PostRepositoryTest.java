package bulletin_board.bulletin_board.repository;

import bulletin_board.bulletin_board.domain.Member;
import bulletin_board.bulletin_board.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    Member.MemberBuilder memberBuilder;
    Post.PostBuilder postBuilder;

    @BeforeEach
    void before(){
        memberBuilder = Member.builder()
                .name("hyunwon")
                .nickname("gimguy")
                .id("khw7385")
                .password("1234")
                .date_of_birth(LocalDate.of(1999, 1, 27))
                .create_time(LocalDateTime.now().withNano(0));

        Member member = memberBuilder.build();
        memberRepository.save(member);


        postBuilder = Post.builder()
                .title("글 제목")
                .text("글 내용")
                .member(member)
                .create_time(LocalDateTime.of(LocalDate.of(2023, 10, 29), LocalTime.of(9, 0)))
                .update_time(null);
    }

    @Test
    void 글등록_및_조회(){
        Post post = postBuilder.build();

        postRepository.save(post);

        entityManager.clear();

        Post findPost = postRepository.findById(post.getPost_id()).get();
        assertThat(post.getPost_id()).isEqualTo(findPost.getPost_id());
        assertThat(post.getText()).isEqualTo(findPost.getText());
        assertThat(post.getMember().getMember_id()).isEqualTo(findPost.getMember().getMember_id());
        assertThat(post.getCreate_time()).isEqualTo(findPost.getCreate_time());
    }
}