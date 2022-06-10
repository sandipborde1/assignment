package nl.xyz.assignment.recipes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/* Custom Exception already created and to be used */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid Credentials!!")
public class InvalidCredentialsException extends RuntimeException {
}
