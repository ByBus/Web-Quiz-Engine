package engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "quizzes")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class QuizEntity {

    @Id
    @GeneratedValue
    private int id;

    @NonNull
    private String title;
    @NonNull
    private String text;
    @NonNull
    @ElementCollection
    private List<String> options;
    @NonNull
    @ElementCollection
    private List<Integer> answer;
}
