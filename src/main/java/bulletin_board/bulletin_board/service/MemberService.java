package bulletin_board.bulletin_board.service;

import bulletin_board.bulletin_board.domain.Member;
import bulletin_board.bulletin_board.dto.MemberLoginRequest;
import bulletin_board.bulletin_board.dto.MemberSignUpRequest;
import bulletin_board.bulletin_board.dto.MemberUpdateRequest;
import bulletin_board.bulletin_board.exception.MemberException;
import bulletin_board.bulletin_board.exception.MemberExceptionType;
import bulletin_board.bulletin_board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 등록
    @Transactional
    public void signUp(MemberSignUpRequest memberSignUpRequest){
        Member findMemberByLogin_id = memberRepository.findByLogin_id(memberSignUpRequest.getLogin_id()).orElse(null);
        Member findMemberByNickname = memberRepository.findByNickname(memberSignUpRequest.getNickname()).orElse(null);

        if (findMemberByLogin_id == null && findMemberByNickname == null){
            Member member = MemberSignUpRequest.toEntity(memberSignUpRequest);
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
    public void login(MemberLoginRequest memberLoginRequest) {
        // 아이디로 회원 조회
        Member findMember = memberRepository.findByLogin_id(memberLoginRequest.getId()).orElseThrow(() -> {
            throw new MemberException(MemberExceptionType.NOT_EXIST_ID);
        });

        // 비밀번호가 동일하지 않을 때
        if (!findMember.getPassword().equals(memberLoginRequest.getPassword())){
            throw new MemberException(MemberExceptionType.NOT_MATCH_ID_AND_PASSWORD);
        }
    }
    // 이거 수정해야 함!!!
    // 회원 수정
    @Transactional
    public void updateMember(Integer member_id, MemberUpdateRequest memberUpdateRequest){
        Member loginMember = memberRepository.findById(member_id).get();
        // 로그인 아이디 와 닉네임 둘 다 바뀔 때
        if (!(loginMember.getLogin_id().equals(memberUpdateRequest.getLogin_id()) || loginMember.getNickname().equals(memberUpdateRequest.getNickname()))){
            Member findMemberByLogin_id = memberRepository.findByLogin_id(memberUpdateRequest.getLogin_id()).orElse(null);
            Member findMemberByNickname = memberRepository.findByNickname(memberUpdateRequest.getNickname()).orElse(null);

            if (findMemberByLogin_id != null && findMemberByNickname != null) throw new MemberException(MemberExceptionType.ALREADY_EXIST_ID_AND_NICKNAME);
            else{
                if (findMemberByLogin_id != null) throw new MemberException(MemberExceptionType.ALREADY_EXIST_ID);
                else if (findMemberByNickname != null) throw new MemberException(MemberExceptionType.ALREADY_EXIST_NICKNAME);
            }
        } // 로그인 아이디만 바뀔 때
        else if (!loginMember.getLogin_id().equals(memberUpdateRequest.getLogin_id())){
            memberRepository.findByLogin_id(memberUpdateRequest.getLogin_id()).ifPresent((login_id) -> {throw new MemberException(MemberExceptionType.ALREADY_EXIST_ID);});
        } // 닉네임만 바뀔 때
        else if(!loginMember.getNickname().equals(memberUpdateRequest.getNickname())){
            memberRepository.findByNickname(memberUpdateRequest.getNickname()).ifPresent((nickname) ->{throw new MemberException(MemberExceptionType.ALREADY_EXIST_NICKNAME);});
        }

        Member member = MemberUpdateRequest.toEntity(member_id, memberUpdateRequest);

        memberRepository.save(member);
    }
}
