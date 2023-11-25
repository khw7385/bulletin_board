package bulletin_board.bulletin_board.dto;

import bulletin_board.bulletin_board.domain.Comment;
import bulletin_board.bulletin_board.domain.Member;
import bulletin_board.bulletin_board.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentLeaveRequestDto {
    private String text;
    private int parent_comment;

    @Builder
    public CommentLeaveRequestDto(String text, int parent_comment) {
        this.text = text;
        this.parent_comment = parent_comment;
    }

    public static Comment toEntity(Member member, Post post, CommentLeaveRequestDto commentLeaveRequestDto){
        return Comment.builder()
                .member(member)
                .post(post)
                .text(commentLeaveRequestDto.getText()).build();
    }
}
