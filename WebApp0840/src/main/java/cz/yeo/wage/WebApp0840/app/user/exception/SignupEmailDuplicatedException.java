package cz.yeo.wage.WebApp0840.app.user.exception;

public class SignupEmailDuplicatedException extends RuntimeException {
    public SignupEmailDuplicatedException(String message) {
        super(message);
    }
}
