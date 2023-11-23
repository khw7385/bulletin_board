package bulletin_board.bulletin_board.service;

import bulletin_board.bulletin_board.domain.Member;
import bulletin_board.bulletin_board.dto.MemberLoginRequestDto;
import bulletin_board.bulletin_board.dto.MemberSignUpRequestDto;
import bulletin_board.bulletin_board.dto.MemberUpdateRequestDto;
import bulletin_board.bulletin_board.exception.MemberException;
import bulletin_board.bulletin_board.exception.MemberExceptionType;
import bulletin_board.bulletin_board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    // 회원 등록
    @Transactional
    public void signUp(MemberSignUpRequestDto memberSignUpRequestDto){
        Member findMemberByLogin_id = memberRepository.findByLogin_id(memberSignUpRequestDto.getLogin_id()).orElse(null);
        Member findMemberByNickname = memberRepository.findByNickname(memberSignUpRequestDto.getNickname()).orElse(null);

        if (findMemberByLogin_id == null && findMemberByNickname == null){
            Member member = MemberSignUpRequestDto.toEntity(memberSignUpRequestDto);
            memberRepository.save(member);
        }else{
            if(findMemberByLogin_id == null){
                throw new MemberException(MemberExceptionType.ALREADY_EXIST_NICKNAME);
            } else if(findMemberByNickname == null){
                throw new MemberException(MemberExceptionType.ALREADY_EXIST_ID);
            } else{
                throw new MemberException(MemberExceptionType.ALREADY_EXIST_ID_AND_NICKNAME);
            }
        }
    }
    // 로그인
    @Transactional
    public void login(MemberLoginRequestDto memberLoginRequestDto) {
        // 아이디로 회원 조회
        Member findMember = memberRepository.findByLogin_id(memberLoginRequestDto.getId()).orElseThrow(() -> {
            throw new MemberException(MemberExceptionType.NOT_EXIST_ID);
        });

        // 비밀번호가 동일하지 않을 때
        if (!findMember.getPassword().equals(memberLoginRequestDto.getPassword())){
            throw new MemberException(MemberExceptionType.NOT_MATCH_ID_AND_PASSWORD);
        }
    }
    // 회원 수정
    @Transactional
    public void updateMember(Integer member_id, MemberUpdateRequestDto memberUpdateRequestDto){
        Member loginMember = memberRepository.findById(member_id).get();
        // 로그인 아이디 와 닉네임 둘 다 바뀔 때
        if (!(loginMember.getLogin_id().equals(memberUpdateRequestDto.getLogin_id()) || loginMember.getNickname().equals(memberUpdateRequestDto.getNickname()))){
            Member findMemberByLogin_id = memberRepository.findByLogin_id(memberUpdateRequestDto.getLogin_id()).orElse(null);
            Member findMemberByNickname = memberRepository.findByNickname(memberUpdateRequestDto.getNickname()).orElse(null);

            if (findMemberByLogin_id != null && findMemberByNickname != null) throw new MemberException(MemberExceptionType.ALREADY_EXIST_ID_AND_NICKNAME);
            else{
                if (findMemberByLogin_id != null) throw new MemberException(MemberExceptionType.ALREADY_EXIST_ID);
                else if (findMemberByNickname != null) throw new MemberException(MemberExceptionType.ALREADY_EXIST_NICKNAME);
            }
        } // 로그인 아이디만 바뀔 때
        else if (!loginMember.getLogin_id().equals(memberUpdateRequestDto.getLogin_id())){
            memberRepository.findByLogin_id(memberUpdateRequestDto.getLogin_id()).ifPresent((login_id) -> {throw new MemberException(MemberExceptionType.ALREADY_EXIST_ID);});
        } // 닉네임만 바뀔 때
        else if(!loginMember.getNickname().equals(memberUpdateRequestDto.getNickname())){
            memberRepository.findByNickname(memberUpdateRequestDto.getNickname()).ifPresent((nickname) ->{throw new MemberException(MemberExceptionType.ALREADY_EXIST_NICKNAME);});
        }

        Member member = MemberUpdateRequestDto.toEntity(member_id, memberUpdateRequestDto);

        memberRepository.save(member);
    }
}
