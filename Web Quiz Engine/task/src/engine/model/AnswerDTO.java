package engine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AnswerDTO {
    private List<Integer> answer;
}
