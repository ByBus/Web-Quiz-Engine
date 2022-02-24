package engine.buiseness;

import java.util.Collections;
import java.util.List;

public class AnswerChecker implements Checker {
    private final List<Integer> answers;
    private final List<Integer> answersToCheck;

    public AnswerChecker(List<Integer> answers, List<Integer> answersToCheck) {
        this.answers = answers;
        this.answersToCheck = answersToCheck;
    }

    @Override
    public boolean check() {
        List<Integer> quizAnswers = answers == null ? Collections.emptyList() : answers;
        return quizAnswers.size() == answersToCheck.size() && quizAnswers.containsAll(answersToCheck);
    }
}
