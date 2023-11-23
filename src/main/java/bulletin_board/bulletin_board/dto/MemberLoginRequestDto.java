package bulletin_board.bulletin_board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberLoginRequestDto {
    private String id;
    private String password;
    @Builder
    public MemberLoginRequestDto(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
