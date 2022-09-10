package cz.yeo.wage.WebApp0840.app.user.exception;

public class SignupLoginIdDuplicatedException extends RuntimeException {
    public SignupLoginIdDuplicatedException(String message) {
        super(message);
    }
}
