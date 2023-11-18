package bulletin_board.bulletin_board.dto;

import bulletin_board.bulletin_board.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberLoginRequest {
    private String id;
    private String password;
    @Builder
    public MemberLoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
