package engine.repository;

import engine.exceptions.QuizNotFoundException;
import engine.model.CorrectQuizAnswerEntity;
import engine.model.QuizEntity;
import engine.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {
    @Autowired
    private final UserRepository userRepository;
    private final QuizPagingRepository quizRepository;
    private final CorrectQuizAnswerPagingRepository answeredQuizRepository;

    public RepositoryService(UserRepository userRepository,
                             QuizPagingRepository quizPagingRepository,
                             CorrectQuizAnswerPagingRepository answeredQuizRepository) {
        this.userRepository = userRepository;
        this.quizRepository = quizPagingRepository;
        this.answeredQuizRepository = answeredQuizRepository;
    }

    public QuizEntity save(QuizEntity quiz) {
        return quizRepository.save(quiz);
    }

    public QuizEntity getQuizById(int id) {
        return quizRepository.findById(id).orElseThrow(QuizNotFoundException::new);
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

    public Page<QuizEntity> getAllQuizzesPaging(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id"));
        return quizRepository.findAll(paging);
    }

    public Page<CorrectQuizAnswerEntity> getCorrectAnswersOfUserPaging(Integer pageNo,
                                                                       Integer pageSize,
                                                                       UserEntity user) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("completedAt").descending());
        return answeredQuizRepository.findAllByUser(user, paging);
    }

    public CorrectQuizAnswerEntity save(CorrectQuizAnswerEntity answer) {
        return answeredQuizRepository.save(answer);
    }
}
