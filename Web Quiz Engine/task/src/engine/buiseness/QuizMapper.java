package engine.buiseness;

import engine.model.QuizDTO;
import engine.model.QuizEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class QuizMapper implements Mapper<QuizDTO, QuizEntity> {
    @Override
    public QuizEntity mapToEntity(QuizDTO dto) {
        return new QuizEntity(dto.getTitle(),
                dto.getText(),
                dto.getOptions(),
                dto.getAnswer() == null ? Collections.emptyList() : dto.getAnswer()
        );
    }

    @Override
    public QuizDTO mapToDTO(QuizEntity entity) {
        return new QuizDTO(entity.getId(),
                entity.getTitle(),
                entity.getText(),
                entity.getOptions(),
                entity.getAnswer()
        );
    }
}
