package engine.repository;

import engine.model.Quiz;

import java.util.List;

public interface Saver {
    Quiz save(Quiz quiz);

    Quiz restore(int id) throws QuizNotFoundException;

    List<Quiz> restoreAll();
}
