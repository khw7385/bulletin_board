package bulletin_board.bulletin_board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int post_id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDateTime create_time;

    private LocalDateTime update_time;

    @OneToMany(mappedBy = "post")
    List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(int post_id, String title, String text, Member member, LocalDateTime create_time, LocalDateTime update_time) {
        this.post_id = post_id;
        this.title = title;
        this.text = text;
        this.member = member;
        this.create_time = create_time;
        this.update_time = update_time;
    }
    // 회원 - 글 편의 메소드
    void setMember(Member member){
        this.member = member;
        member.getPosts().add(this);
    }
}
