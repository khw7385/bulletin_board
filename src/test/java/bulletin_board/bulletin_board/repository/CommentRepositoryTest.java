package bulletin_board.bulletin_board.repository;

import bulletin_board.bulletin_board.domain.Comment;
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

@SpringBootTest
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    Comment.CommentBuilder commentBuilder;
    Post.PostBuilder postBuilder;

    Member.MemberBuilder memberBuilder;
    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void before(){
        memberBuilder = Member.builder()
                .name("hyunwon")
                .nickname("gimguy")
                .login_id("khw7385")
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

        Post post = postBuilder.build();
        postRepository.save(post);

        commentBuilder = Comment.builder()
                .create_time(LocalDateTime.of(LocalDate.of(2023, 10, 31), LocalTime.of(12, 30)))
                .update_time(LocalDateTime.now().withNano(0))
                .post(post)
                .member(member)
                .text("댓글")
                .parent_comment(0);
    }

    @Test
    void 댓글등록_및_조회(){
        Comment comment = commentBuilder.build();

        commentRepository.save(comment);

        entityManager.clear();

        Comment findComment = commentRepository.findById(comment.getComment_id()).get();

        assertThat(comment.getComment_id()).isEqualTo(findComment.getComment_id());
        assertThat(comment.getText()).isEqualTo(findComment.getText());
        assertThat(comment.getMember().getMember_id()).isEqualTo(findComment.getMember().getMember_id());
        assertThat(comment.getPost().getPost_id()).isEqualTo(findComment.getPost().getPost_id());
        assertThat(comment.getUpdate_time()).isEqualTo(comment.getUpdate_time());
    }


}
