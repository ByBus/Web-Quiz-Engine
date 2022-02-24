package engine.repository;

import engine.model.Quiz;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Memory implements Saver {
    private final Map<Integer, Quiz> savedQuizzes = new HashMap<>();
    private int nextId = 0;

    @Override
    public Quiz save(Quiz quiz) {
        int id = nextId++;
        quiz.setId(id);
        savedQuizzes.put(id, quiz);
        return quiz;
    }

    @Override
    public Quiz restore(int id) {
        Quiz quiz = savedQuizzes.get(id);
        if (quiz == null) {
            throw new QuizNotFoundException();
        }
        return savedQuizzes.get(id);
    }

    @Override
    public List<Quiz> restoreAll() {
        return new ArrayList<>(savedQuizzes.values());
    }

}
