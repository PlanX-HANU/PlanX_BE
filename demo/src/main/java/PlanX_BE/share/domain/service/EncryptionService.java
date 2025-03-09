package PlanX_BE.share.domain.service;


import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.exception.ValidateException;
import PlanX_BE.share.domain.model.Result;
import lombok.SneakyThrows;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {


    public Result<String, DomainException> encryption(String rawText){
        try {
            String encrypted = BCrypt.hashpw(rawText, BCrypt.gensalt());
            return Result.success(encrypted);
        }
        catch (Exception e) {
            return Result.failed(new DomainException("ERROR", "INTERNAL_ERROR"));
        }
    }

    @SneakyThrows
    public Result<Boolean, ValidateException> checkValidate(String inputText , String textToCompare) {
        try {
            String hashed = BCrypt.hashpw(inputText, BCrypt.gensalt());
            Boolean isMatch = BCrypt.checkpw(textToCompare, hashed);
            if (!isMatch) {
                return Result.failed(new ValidateException("Input Mismatch"));

            } else {
                return Result.success(true);
            }
        } catch (Exception e) {
            throw new DomainException(e.getMessage(), "INTERNAL_ERROR");
        }
    }

}
