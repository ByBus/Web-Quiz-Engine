package engine.repository;

import engine.exceptions.QuizNotFoundException;
import engine.model.QuizEntity;
import engine.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {
    @Autowired
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public RepositoryService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    public QuizEntity save(QuizEntity quiz) {
        return quizRepository.save(quiz);
    }

    public QuizEntity getQuizById(int id) {
        return quizRepository.findById(id).orElseThrow(QuizNotFoundException::new);
    }

    public List<QuizEntity> getAll() {
        return (List<QuizEntity>) quizRepository.findAll();
    }

    public UserEntity save (UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }

    public void delete(QuizEntity quiz) {
        quizRepository.delete(quiz);
    }
}
