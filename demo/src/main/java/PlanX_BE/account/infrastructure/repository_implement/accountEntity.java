package PlanX_BE.account.infrastructure.repository_implement;

import PlanX_BE.account.domain.model.ACCOUNT_ROLE;

import PlanX_BE.account.domain.model.CreateRequest;
import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.model.Result;
import PlanX_BE.share.domain.service.EncryptionService;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "account")
@Builder
@Data
public class accountEntity {
    private final ObjectId accountID;
    private final String email;
    private final String password;
    private final LocalDateTime createdDate;
    private final ACCOUNT_ROLE role;

    @Autowired
    private static  EncryptionService encryptionService;


    public static Result<accountEntity, DomainException> createNewAccount(CreateRequest request){
        try{

            return Result.success(accountEntity.builder()
                            .email(request.getEmail())
                            .password(encryptionService.encryption(request.getPassword()).getSuccessData())
                            .createdDate(LocalDateTime.now())
                    .build());
        }
        catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
        }
    }

}
