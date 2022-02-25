package engine.presentation;

import engine.buiseness.AnswerChecker;
import engine.buiseness.Checker;
import engine.buiseness.Mapper;
import engine.model.*;
import engine.repository.QuizRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestApiController {
    private final QuizRepositoryService repository;
    private final Mapper<QuizDTO, QuizEntity> mapper;

    @Autowired
    public RestApiController(QuizRepositoryService repository,
                             Mapper<QuizDTO, QuizEntity> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping("/api/quizzes")
    public QuizDTO createQuiz(@Valid @RequestBody QuizDTO newQuiz) {
        QuizEntity savedQuiz = repository.save(mapper.mapToEntity(newQuiz));
        newQuiz.setId(savedQuiz.getId());
        return newQuiz;
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizDTO getQuizById(@PathVariable int id) {
        return mapper.mapToDTO(repository.getById(id));
    }

    @GetMapping("/api/quizzes")
    public List<QuizDTO> getAllQuizzes() {
        return repository.getAll().stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Feedback> checkAnswer(@PathVariable int id, @RequestBody Answer answer) {
        QuizEntity quiz = repository.getById(id);
        Checker answerChecker = new AnswerChecker(quiz.getAnswer(), answer.getAnswer());
        Feedback feedback = answerChecker.check() ? new CorrectFeedback() : new WrongFeedback();
        return ResponseEntity.ok(feedback);
    }
}