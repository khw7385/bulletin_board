package bulletin_board.bulletin_board.dto;

import bulletin_board.bulletin_board.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class MemberUpdateRequest {
    private String login_id;
    private String password;
    private String name;
    private String nickname;

    @Builder
    public MemberUpdateRequest(String login_id, String password, String name, String nickname) {
        this.login_id = login_id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }

    public static Member toEntity(int member_id, MemberUpdateRequest memberUpdateRequest){
        return Member.builder()
                .member_id(member_id)
                .login_id(memberUpdateRequest.getLogin_id())
                .password(memberUpdateRequest.getPassword())
                .name(memberUpdateRequest.getName())
                .nickname(memberUpdateRequest.getNickname()).build();
    }
}
