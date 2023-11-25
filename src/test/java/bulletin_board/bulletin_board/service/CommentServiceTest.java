package bulletin_board.bulletin_board.service;

import bulletin_board.bulletin_board.dto.CommentLeaveRequestDto;
import bulletin_board.bulletin_board.dto.MemberSignUpRequestDto;
import bulletin_board.bulletin_board.dto.PostRequestDto;
import bulletin_board.bulletin_board.repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;
    @BeforeEach
    void before(){
        MemberSignUpRequestDto memberSignUpRequestDto = MemberSignUpRequestDto.builder()
                .login_id("khw7385")
                .password("1234")
                .name("hyunwon")
                .nickname("gimguy")
                .date_of_birth(LocalDate.of(1999, 1, 27)).build();

        memberService.signUp(memberSignUpRequestDto);


        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("글 제목")
                .text("본문").build();

        postService.post(1, postRequestDto);
    }


    @Test
    void 댓글_등록(){
        CommentLeaveRequestDto commentLeaveRequestDto = CommentLeaveRequestDto.builder()
                .text("댓글1")
                .parent_comment(0).build();

        commentService.leaveComment(1, 2, commentLeaveRequestDto);
    }

    @Test
    void 댓글_삭제(){
        // 댓글 저장
        CommentLeaveRequestDto commentLeaveRequestDto = CommentLeaveRequestDto.builder()
                .text("댓글1")
                .parent_comment(0).build();

        commentService.leaveComment(1, 2, commentLeaveRequestDto);

        // 댓글 삭제
        commentService.removeComment(3);

        assertThatThrownBy(() -> commentRepository.findById(3).get()).isInstanceOf(NoSuchElementException.class);
    }
}