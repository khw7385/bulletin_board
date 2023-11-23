package bulletin_board.bulletin_board.service;

import bulletin_board.bulletin_board.domain.Member;
import bulletin_board.bulletin_board.dto.MemberLoginRequestDto;
import bulletin_board.bulletin_board.dto.MemberSignUpRequestDto;
import bulletin_board.bulletin_board.dto.MemberUpdateRequestDto;
import bulletin_board.bulletin_board.exception.MemberException;
import bulletin_board.bulletin_board.exception.MemberExceptionType;
import bulletin_board.bulletin_board.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @BeforeEach
    void before(){
        Member member1 = Member.builder()
                .name("hyunwon")
                .nickname("gimguy")
                .login_id("khw7385")
                .password("1234")
                .date_of_birth(LocalDate.of(1999, 1, 27))
                .create_time(LocalDateTime.now().withNano(0)).build();

        Member member2 = Member.builder()
                .name("ziho")
                .nickname("zico")
                .login_id("zico1234")
                .password("12345")
                .date_of_birth(LocalDate.of(1992, 9, 14))
                .create_time(LocalDateTime.now().withNano(0)).build();

        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @Test
    void registerMemberPass(){
        MemberSignUpRequestDto memberSignUpRequestDto = MemberSignUpRequestDto.builder()
                .login_id("qwer123")
                .password("qwer123")
                .name("spring")
                .nickname("spring")
                .date_of_birth(LocalDate.of(2023, 11, 16)).build();

        memberService.signUp(memberSignUpRequestDto);
    }

    @Test
    void registerMemberThrownByMemberExceptionTypeAsAlreadyExistIDAndNickname(){
        MemberSignUpRequestDto memberSignUpRequestDto = MemberSignUpRequestDto.builder()
                .login_id("khw7385")
                .password("qwer123")
                .name("spring")
                .nickname("zico")
                .date_of_birth(LocalDate.of(2023, 11, 16)).build();

        assertThatThrownBy(()-> memberService.signUp(memberSignUpRequestDto)).isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("exceptionType", MemberExceptionType.ALREADY_EXIST_ID_AND_NICKNAME);
    }
    @Test
    void 이미_로그인아이디가_존재하는_회원등록(){
        MemberSignUpRequestDto memberSignUpRequestDto = MemberSignUpRequestDto.builder()
                .login_id("khw7385")
                .password("qwer123")
                .name("spring")
                .nickname("spring")
                .date_of_birth(LocalDate.of(2023, 11, 18)).build();
        assertThatThrownBy(() -> memberService.signUp(memberSignUpRequestDto)).isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("exceptionType", MemberExceptionType.ALREADY_EXIST_ID);
    }

    @Test
    void 이미_닉네임이_존재하는_회원등록(){
        MemberSignUpRequestDto memberSignUpRequestDto = MemberSignUpRequestDto.builder()
                .login_id("qwer123")
                .password("qwer123")
                .name("spring")
                .nickname("gimguy")
                .date_of_birth(LocalDate.of(2023, 11, 18)).build();
        assertThatThrownBy(() -> memberService.signUp(memberSignUpRequestDto)).isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("exceptionType", MemberExceptionType.ALREADY_EXIST_NICKNAME);
    }

    @Test
    void 로그인성공(){
        MemberLoginRequestDto loginRequest = MemberLoginRequestDto.builder()
                .id("khw7385")
                .password("1234").build();
        assertDoesNotThrow(() -> memberService.login(loginRequest));
    }
    @Test
    void 로그인실패_존재하지_않는_아이디(){
        MemberLoginRequestDto loginRequest = MemberLoginRequestDto.builder()
                .id("aaa123")
                .password("1234").build();
        assertThatThrownBy(() -> memberService.login(loginRequest)).isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("exceptionType", MemberExceptionType.NOT_EXIST_ID);
    }

    @Test
    void 로그인실패_아이디와_비밀번호_불일치(){
        MemberLoginRequestDto loginRequest = MemberLoginRequestDto.builder()
                .id("khw7385")
                .password("12345").build();
        assertThatThrownBy(() -> memberService.login(loginRequest)).isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("exceptionType", MemberExceptionType.NOT_MATCH_ID_AND_PASSWORD);
    }

    @Test
    void 회원수정_성공(){
        MemberUpdateRequestDto updateRequest = MemberUpdateRequestDto.builder()
                .login_id("khw73850")
                .password("1234")
                .name("hyunwon")
                .nickname("amor manet").build();

        assertDoesNotThrow(() -> memberService.updateMember(1, updateRequest));
    }

    @Test
    void 회원수정_실패_존재하는_로그인_아이디와_닉네임(){
        MemberUpdateRequestDto updateRequest = MemberUpdateRequestDto.builder()
                .login_id("zico1234")
                .password("1234")
                .name("hyunwon")
                .nickname("zico").build();
        assertThatThrownBy(() -> memberService.updateMember(1, updateRequest)).isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("exceptionType", MemberExceptionType.ALREADY_EXIST_ID_AND_NICKNAME);
    }

    @Test
    void 회원수정_실패_존재하는_로그인_아이디(){
        MemberUpdateRequestDto updateRequest = MemberUpdateRequestDto.builder()
                .login_id("zico1234")
                .password("1234")
                .name("hyunwon")
                .nickname("gimguy").build();
        assertThatThrownBy(() -> memberService.updateMember(1, updateRequest)).isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("exceptionType", MemberExceptionType.ALREADY_EXIST_ID);
    }

    @Test
    void 회원수정_실패_존재하는_닉네임(){
        MemberUpdateRequestDto updateRequest = MemberUpdateRequestDto.builder()
                .login_id("khw7385")
                .password("1234")
                .name("hyunwon")
                .nickname("zico").build();
        assertThatThrownBy(() -> memberService.updateMember(1, updateRequest)).isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("exceptionType", MemberExceptionType.ALREADY_EXIST_NICKNAME);
    }
}