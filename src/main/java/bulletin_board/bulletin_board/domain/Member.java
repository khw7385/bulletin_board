package bulletin_board.bulletin_board.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int member_id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 30)
    private String login_id;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDate date_of_birth;

    @Column(nullable = false, updatable = false)
    private LocalDateTime create_time;

    @OneToMany(mappedBy = "member")
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(int member_id, String name, String nickname, String login_id, String password, LocalDate date_of_birth, LocalDateTime create_time) {
        this.member_id = member_id;
        this.name = name;
        this.nickname = nickname;
        this.login_id = login_id;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.create_time = create_time;
    }
    @PrePersist
    public void setCreate_time(){
        this.create_time = LocalDateTime.now();
    }


}
