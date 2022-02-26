package engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @Email(message = "Email is not valid", regexp = ".+@.+\\..+")
    @NotEmpty
    String email;
    @NotBlank
    @Size(min = 5)
    String password;
}