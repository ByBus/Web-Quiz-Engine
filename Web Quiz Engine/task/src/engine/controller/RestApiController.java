package engine.controller;

import engine.buiseness.AnswerChecker;
import engine.buiseness.Checker;
import engine.model.*;
import engine.repository.Saver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RestApiController {
    Saver repository;

    @Autowired
    public RestApiController(Saver memory) {
        this.repository = memory;
    }

    @PostMapping("/api/quizzes")
    public Quiz createQuiz(@Valid @RequestBody Quiz newQuiz) {
        return repository.save(newQuiz);
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        return repository.restore(id);
    }

    @GetMapping("/api/quizzes")
    public List<Quiz> getAllQuizzes() {
        return repository.restoreAll();
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Feedback> checkAnswer(@PathVariable int id, @RequestBody Answer answer) {
        Quiz quiz = repository.restore(id);
        Checker answerChecker = new AnswerChecker(quiz.getAnswer(), answer.getAnswer());
        Feedback feedback = answerChecker.check() ? new CorrectFeedback() : new WrongFeedback();
        return ResponseEntity.ok(feedback);
    }
}