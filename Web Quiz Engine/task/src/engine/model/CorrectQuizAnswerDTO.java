package engine.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CorrectQuizAnswerDTO {
    private int id;
    private LocalDateTime completedAt;
}
