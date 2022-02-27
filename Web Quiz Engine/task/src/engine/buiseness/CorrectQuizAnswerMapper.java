package engine.buiseness;

import engine.model.CorrectQuizAnswerDTO;
import engine.model.CorrectQuizAnswerEntity;
import org.springframework.stereotype.Component;

@Component
public class CorrectQuizAnswerMapper implements Mapper<CorrectQuizAnswerDTO, CorrectQuizAnswerEntity> {
    @Override
    public CorrectQuizAnswerEntity mapToEntity(CorrectQuizAnswerDTO objFrom) {
        return null;
    }

    @Override
    public CorrectQuizAnswerDTO mapToDTO(CorrectQuizAnswerEntity entity) {
        return new CorrectQuizAnswerDTO(entity.getQuizId(), entity.getCompletedAt());
    }
}
