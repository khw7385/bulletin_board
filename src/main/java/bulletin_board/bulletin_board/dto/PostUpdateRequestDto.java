package bulletin_board.bulletin_board.dto;

import bulletin_board.bulletin_board.domain.Member;
import bulletin_board.bulletin_board.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostUpdateRequestDto {
    private String title;
    private String text;

    @Builder
    public PostUpdateRequestDto(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public static Post toEntity(int post_id, Member member, LocalDateTime create_time, PostUpdateRequestDto postUpdateRequestDto) {
        return Post.builder()
                .post_id(post_id)
                .title(postUpdateRequestDto.getTitle())
                .text(postUpdateRequestDto.getText())
                .create_time(create_time)
                .member(member).build();
    }
}
