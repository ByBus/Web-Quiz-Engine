package engine.presentation;

import engine.buiseness.AnswerChecker;
import engine.buiseness.Checker;
import engine.buiseness.Mapper;
import engine.exceptions.BadRequestException;
import engine.exceptions.ForbiddenException;
import engine.exceptions.QuizNotFoundException;
import engine.exceptions.UnauthorizedException;
import engine.model.*;
import engine.repository.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class RestApiController {
    private final RepositoryService repository;
    private final Mapper<QuizDTO, QuizEntity> mapper;
    private final PasswordEncoder encoder;

    @Autowired
    public RestApiController(RepositoryService repository,
                             Mapper<QuizDTO, QuizEntity> mapper,
                             PasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    @PostMapping("/api/quizzes")
    public QuizDTO createQuiz(@AuthenticationPrincipal UserDetails details,
                              @Valid @RequestBody QuizDTO newQuiz) {
        if (details == null) {
            throw new UnauthorizedException();
        }
        UserEntity user = repository.getUserByEmail(details.getUsername());
        QuizEntity quiz = mapper.mapToEntity(newQuiz);
        quiz.setUser(user);
        QuizEntity savedQuiz = repository.save(quiz);
        newQuiz.setId(savedQuiz.getId());
        return newQuiz;
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizDTO getQuizById(@AuthenticationPrincipal UserDetails details,
                               @PathVariable int id) {
        if (details == null) {
            throw new UnauthorizedException();
        }
        QuizEntity quiz = repository.getQuizById(id);
        return mapper.mapToDTO(quiz);
    }

    @GetMapping("/api/quizzes")
    public List<QuizDTO> getAllQuizzes(@AuthenticationPrincipal UserDetails details) {
        if (details == null) {
            throw new UnauthorizedException();
        }
        return repository.getAll().stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Feedback> checkAnswer(@AuthenticationPrincipal UserDetails details,
                                                @PathVariable int id,
                                                @RequestBody Answer answer) {
        if (details == null) {
            throw new UnauthorizedException();
        }
        QuizEntity quiz = repository.getQuizById(id);
        Checker answerChecker = new AnswerChecker(quiz.getAnswer(), answer.getAnswer());
        Feedback feedback = answerChecker.check() ? new CorrectFeedback() : new WrongFeedback();
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
        if (details == null) {
            throw new UnauthorizedException();
        }
        UserEntity user = repository.getUserByEmail(details.getUsername());
        QuizEntity quiz = repository.getQuizById(id);
        if (Objects.equals(quiz.getUser(), user)) {
            repository.delete(quiz);
            return ResponseEntity.noContent().build();
        } else {
            throw new ForbiddenException();
        }
    }
}