package PlanX_BE.share.domain.exception;

public class NotFoundException extends DomainException {
    public NotFoundException(String message) {
        super(message, "NOT_FOUND");
    }

    public NotFoundException(Exception e) {
        super(e.getMessage(), "NOT_FOUND");
    }

}