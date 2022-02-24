package engine.controller;

import engine.model.Quiz;
import engine.model.Result;
import engine.repository.Saver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestApiController {
    Saver repository;

    @Autowired
    public RestApiController(Saver memory) {
        this.repository = memory;
    }

    @PostMapping("/api/quizzes")
    public Quiz createQuiz(@RequestBody Quiz newQuiz) {
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
    public ResponseEntity<Result> checkAnswer(@PathVariable int id, @RequestParam int answer) {
        Quiz quiz = repository.restore(id);
        boolean isCorrect = answer == quiz.getAnswer();
        String feedback = isCorrect ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
        return ResponseEntity.ok(new Result(isCorrect, feedback));
    }
}