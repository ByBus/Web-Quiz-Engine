package engine.repository;

import engine.model.QuizEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizRepositoryService {
    @Autowired
    QuizRepository repository;

    public QuizRepositoryService(QuizRepository repository) {
        this.repository = repository;
    }

    public QuizEntity save(QuizEntity quiz) {
        return repository.save(quiz);
    }

    public QuizEntity getById(int id) {
        return repository.findById(id).orElseThrow(QuizNotFoundException::new);
    }

    public List<QuizEntity> getAll() {
        return (List<QuizEntity>) repository.findAll();
    }

}
