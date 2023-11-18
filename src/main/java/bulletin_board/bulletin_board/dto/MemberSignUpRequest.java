package bulletin_board.bulletin_board.dto;

import bulletin_board.bulletin_board.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class MemberSignUpRequest {
    private String login_id;
    private String password;
    private String name;
    private String nickname;
    private LocalDate date_of_birth;

    public static Member toEntity(MemberSignUpRequest memberSignUpRequest){
        return Member.builder()
                .login_id(memberSignUpRequest.getLogin_id())
                .password(memberSignUpRequest.getPassword())
                .name(memberSignUpRequest.getName())
                .nickname(memberSignUpRequest.getNickname())
                .date_of_birth(memberSignUpRequest.getDate_of_birth()).build();
    }
    @Builder
    public MemberSignUpRequest(String login_id, String password, String name, String nickname, LocalDate date_of_birth) {
        this.login_id = login_id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.date_of_birth = date_of_birth;
    }
}
