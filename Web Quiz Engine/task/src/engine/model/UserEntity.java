package engine.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserEntity {
    @Id
    @NonNull
    @Column(name = "email")
    private String username;
    @NonNull
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuizEntity> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CorrectQuizAnswerEntity> correctQuizEntities = new ArrayList<>();

    private String role = "ROLE_USER";
}