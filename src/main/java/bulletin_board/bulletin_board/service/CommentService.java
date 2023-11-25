package bulletin_board.bulletin_board.service;

import bulletin_board.bulletin_board.domain.Comment;
import bulletin_board.bulletin_board.domain.Member;
import bulletin_board.bulletin_board.domain.Post;
import bulletin_board.bulletin_board.dto.CommentLeaveRequestDto;
import bulletin_board.bulletin_board.dto.CommentUpdateRequestDto;
import bulletin_board.bulletin_board.repository.CommentRepository;
import bulletin_board.bulletin_board.repository.MemberRepository;
import bulletin_board.bulletin_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    @Transactional
    void leaveComment(int member_id, int post_id, CommentLeaveRequestDto commentLeaveRequestDto){
        Member findMember = memberRepository.findById(member_id).get();
        Post findPost = postRepository.findById(post_id).get();
        Comment comment = CommentLeaveRequestDto.toEntity(findMember, findPost, commentLeaveRequestDto);
        commentRepository.save(comment);
    }

    @Transactional
    void updateComment(int comment_id, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment findComment = commentRepository.findById(comment_id).get();
        Comment updateComment = CommentUpdateRequestDto.toEntity(findComment, commentUpdateRequestDto);
        commentRepository.save(updateComment);
    }

    @Transactional
    void removeComment(int comment_id){
        Comment removeComment = commentRepository.findById(comment_id).get();
        commentRepository.delete(removeComment);
    }
}
