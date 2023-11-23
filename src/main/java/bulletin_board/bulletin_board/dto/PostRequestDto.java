package bulletin_board.bulletin_board.dto;

import bulletin_board.bulletin_board.domain.Member;
import bulletin_board.bulletin_board.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostRequestDto {
    private String title;
    private String text;

    @Builder
    public PostRequestDto(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public static Post toEntity(Member member, PostRequestDto postRequestDto) {
        return Post.builder()
                .title(postRequestDto.getTitle())
                .text(postRequestDto.getText())
                .member(member)
                .build();
    }
}
