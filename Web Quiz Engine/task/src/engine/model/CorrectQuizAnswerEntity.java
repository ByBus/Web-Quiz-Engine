package engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "correct_quiz_answer")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class CorrectQuizAnswerEntity {
    @Id
    @GeneratedValue
    private int id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="quiz_id", nullable=false)
    private QuizEntity quiz;

    @CreationTimestamp
    private LocalDateTime completedAt;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;
}
