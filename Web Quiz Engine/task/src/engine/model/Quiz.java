package engine.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Quiz {
    private int id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    @NotNull
    @Size(min = 2)
    private List<String> options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;
}
