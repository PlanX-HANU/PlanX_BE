package PlanX_BE.share.domain.exception;

public class ValidateException extends DomainException {
    public ValidateException(String message) {
        super(message, "VALIDATE_FAILED");
    }

    public ValidateException(Exception exception) {
        super(exception.getMessage(), "VALIDATE_FAILED");
    }
}

