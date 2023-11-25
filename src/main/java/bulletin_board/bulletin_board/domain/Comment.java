package bulletin_board.bulletin_board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int comment_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime create_time;

    private LocalDateTime update_time;

    @Column(nullable = true)
    private int parent_comment;

    @Builder
    public Comment(int comment_id, Member member, Post post, String text, LocalDateTime create_time, LocalDateTime update_time, int parent_comment) {
        this.comment_id = comment_id;
        this.member = member;
        this.post = post;
        this.text = text;
        this.create_time = create_time;
        this.update_time = update_time;
        this.parent_comment = parent_comment;
    }

    // 멤버 - 댓글 편의 메소드
    void setMember(Member member){
        this.member = member;
        member.getComments().add(this);
    }
    
    // 글 - 댓글 편의 메소드
    void setPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }

    @PrePersist
    void setCreate_time(){
        this.create_time = LocalDateTime.now();
    }

    @PreUpdate
    void setUpdate_time(){
        this.update_time = LocalDateTime.now();
    }
}
