package bulletin_board.bulletin_board.service;

import bulletin_board.bulletin_board.domain.Member;
import bulletin_board.bulletin_board.domain.Post;
import bulletin_board.bulletin_board.dto.PostRequestDto;
import bulletin_board.bulletin_board.dto.PostUpdateRequestDto;
import bulletin_board.bulletin_board.repository.MemberRepository;
import bulletin_board.bulletin_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    void post(int member_id, PostRequestDto postRequestDto){
        Member postMember = memberRepository.findById(member_id).get();

        Post post = PostRequestDto.toEntity(postMember, postRequestDto);

        postRepository.save(post);
    }

    @Transactional
    void postUpdate(int post_id, PostUpdateRequestDto postUpdateRequestDto) {
        Post findPost = postRepository.findById(post_id).get();

        Post updatePost = PostUpdateRequestDto.toEntity(findPost.getPost_id(), findPost.getMember(), findPost.getCreate_time(), postUpdateRequestDto);

        postRepository.save(updatePost);
    }
}
