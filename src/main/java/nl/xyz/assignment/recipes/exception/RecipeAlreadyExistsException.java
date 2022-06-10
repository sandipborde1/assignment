package nl.xyz.assignment.recipes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/* Custom Exception already created and to be used */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exists")
public class RecipeAlreadyExistsException extends RuntimeException {
}
