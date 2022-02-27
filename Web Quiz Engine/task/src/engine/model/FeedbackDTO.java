package engine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class FeedbackDTO {
    private boolean success;
    private String feedback;

    public static class Correct extends FeedbackDTO {
        public Correct() {
            super(true, "Congratulations, you're right!");
        }
    }

    public static class Wrong extends FeedbackDTO {
        public Wrong() {
            super(false, "Wrong answer! Please, try again.");
        }
    }
}
