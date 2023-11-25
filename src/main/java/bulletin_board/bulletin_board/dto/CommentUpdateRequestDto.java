package bulletin_board.bulletin_board.dto;

import bulletin_board.bulletin_board.domain.Comment;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
    private String text;

    public static Comment toEntity(Comment comment, CommentUpdateRequestDto commentUpdateRequestDto){
        return Comment.builder()
                .comment_id(comment.getComment_id())
                .member(comment.getMember())
                .post(comment.getPost())
                .parent_comment(comment.getParent_comment())
                .create_time(comment.getCreate_time())
                .text(commentUpdateRequestDto.getText()).build();
    }
}
