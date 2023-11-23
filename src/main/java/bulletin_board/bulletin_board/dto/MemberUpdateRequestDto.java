package bulletin_board.bulletin_board.dto;

import bulletin_board.bulletin_board.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateRequestDto {
    private String login_id;
    private String password;
    private String name;
    private String nickname;

    @Builder
    public MemberUpdateRequestDto(String login_id, String password, String name, String nickname) {
        this.login_id = login_id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
    }

    public static Member toEntity(int member_id, MemberUpdateRequestDto memberUpdateRequestDto){
        return Member.builder()
                .member_id(member_id)
                .login_id(memberUpdateRequestDto.getLogin_id())
                .password(memberUpdateRequestDto.getPassword())
                .name(memberUpdateRequestDto.getName())
                .nickname(memberUpdateRequestDto.getNickname()).build();
    }
}
