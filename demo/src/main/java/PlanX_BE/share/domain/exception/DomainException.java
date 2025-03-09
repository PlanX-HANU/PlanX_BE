package PlanX_BE.share.domain.exception;



public class DomainException extends Exception{
    public final String code;


    public DomainException(String message,String code) {
        super(message);
        this.code = code;

    }
}
