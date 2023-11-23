package bulletin_board.bulletin_board.service;

import bulletin_board.bulletin_board.dto.MemberSignUpRequestDto;
import bulletin_board.bulletin_board.dto.PostRequestDto;
import bulletin_board.bulletin_board.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void before(){
        MemberSignUpRequestDto memberSignUpRequestDto = MemberSignUpRequestDto.builder()
                .login_id("khw7385")
                .password("1234")
                .name("hyunwon")
                .nickname("gimguy")
                .date_of_birth(LocalDate.of(1999, 1, 27)).build();
        memberService.signUp(memberSignUpRequestDto);
    }

    @Test
    void 글_등록(){
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("제목")
                .text("바디").build();

        postService.post(1, postRequestDto);
    }

    @Test
    void 글_수정(){
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("제목")
                .text("바디").build();
        postService.post(1, postRequestDto);

        PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .title("제목2")
                .text("바디").build();

        postService.postUpdate(2, postUpdateRequestDto);
    }
}