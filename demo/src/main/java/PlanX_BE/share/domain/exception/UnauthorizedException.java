package PlanX_BE.share.domain.exception;

public class UnauthorizedException extends DomainException{
    public UnauthorizedException(String message) {
        super(message, "NOT_FOUND");
    }

    public UnauthorizedException(Exception e) {
        super(e.getMessage(), "NOT_FOUND");
    }
}
