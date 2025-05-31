package c.example.aibouauth.auth;

import org.springframework.http.HttpStatus;

public class UserNotVerifiedException extends RuntimeException {

    private final HttpStatus status;

    public UserNotVerifiedException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
