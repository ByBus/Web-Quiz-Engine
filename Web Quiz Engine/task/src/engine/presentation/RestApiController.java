package engine.presentation;

import engine.buiseness.AnswerChecker;
import engine.buiseness.Checker;
import engine.buiseness.Mapper;
import engine.exceptions.BadRequestException;
import engine.exceptions.ForbiddenException;
import engine.model.*;
import engine.repository.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
public class RestApiController {
    private final RepositoryService repository;
    private final Mapper<QuizDTO, QuizEntity> quizMapper;
    private final PasswordEncoder encoder;
    private final Mapper<CorrectQuizAnswerDTO, CorrectQuizAnswerEntity> answerMapper;

    @Autowired
    public RestApiController(RepositoryService repository,
                             Mapper<QuizDTO, QuizEntity> quizMapper,
                             PasswordEncoder encoder,
                             Mapper<CorrectQuizAnswerDTO, CorrectQuizAnswerEntity> answerMapper) {
        this.repository = repository;
        this.quizMapper = quizMapper;
        this.encoder = encoder;
        this.answerMapper = answerMapper;
    }

    @PostMapping("/api/quizzes")
    public QuizDTO createQuiz(@AuthenticationPrincipal UserDetails details,
                              @Valid @RequestBody QuizDTO newQuiz) {
        UserEntity user = repository.getUserByEmail(details.getUsername());
        QuizEntity quiz = quizMapper.mapToEntity(newQuiz);
        quiz.setUser(user);
        QuizEntity savedQuiz = repository.save(quiz);
        newQuiz.setId(savedQuiz.getId());
        return newQuiz;
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizDTO getQuizById(@PathVariable int id) {
        QuizEntity quiz = repository.getQuizById(id);
        return quizMapper.mapToDTO(quiz);
    }

    @GetMapping("/api/quizzes")
    public Page<QuizDTO> getAllQuizzes(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        Page<QuizEntity> currentPageEntity = repository.getAllQuizzesPaging(page, size);
        return currentPageEntity.map(quizMapper::mapToDTO);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<FeedbackDTO> checkAnswer(@AuthenticationPrincipal UserDetails details,
                                                   @PathVariable int id,
                                                   @RequestBody AnswerDTO answer) {
        QuizEntity quiz = repository.getQuizById(id);
        Checker answerChecker = new AnswerChecker(quiz.getAnswer(), answer.getAnswer());
        boolean isAnswerCorrect = answerChecker.check();
        FeedbackDTO feedback = isAnswerCorrect ? new FeedbackDTO.Correct() : new FeedbackDTO.Wrong();
        if (isAnswerCorrect) {
            UserEntity user = repository.getUserByEmail(details.getUsername());
            CorrectQuizAnswerEntity correctAnswer = new CorrectQuizAnswerEntity(quiz, user);
            repository.save(correctAnswer);
        }
        return ResponseEntity.ok(feedback);
    }

    @PostMapping("/api/register")
    public ResponseEntity<String> registerNewUser(@Valid @RequestBody UserRegistrationDTO registration) {
        UserEntity userEntity = repository.getUserByEmail(registration.getEmail());
        if (userEntity == null) {
            UserEntity newUser = new UserEntity(registration.getEmail(), encoder.encode(registration.getPassword()));
            repository.save(newUser);
            return new ResponseEntity<>("User Registered", HttpStatus.OK);
        } else {
            throw new BadRequestException();
        }
    }

    @DeleteMapping("api/quizzes/{id}")
    public ResponseEntity<String> deleteQuiz(@AuthenticationPrincipal UserDetails details,
                                             @PathVariable int id) {
        UserEntity user = repository.getUserByEmail(details.getUsername());
        QuizEntity quiz = repository.getQuizById(id);
        if (Objects.equals(quiz.getUser(), user)) {
            repository.delete(quiz);
            return ResponseEntity.noContent().build();
        } else {
            throw new ForbiddenException();
        }
    }

    @GetMapping("/api/quizzes/completed")
    public Page<CorrectQuizAnswerDTO> getCompletedQuizPaging(@AuthenticationPrincipal UserDetails details,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        UserEntity user = repository.getUserByEmail(details.getUsername());
        Page<CorrectQuizAnswerEntity> answerPage = repository.getCorrectAnswersOfUserPaging(page, size, user);

        return answerPage.map(answerMapper::mapToDTO);
    }
}