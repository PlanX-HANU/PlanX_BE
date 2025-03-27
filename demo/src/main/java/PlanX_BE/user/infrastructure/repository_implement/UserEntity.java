package PlanX_BE.user.infrastructure.repository_implement;

import PlanX_BE.account.infrastructure.repository_implement.accountEntity;
import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.model.Result;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "user")
@Data
@Builder
public class UserEntity {

    private final String id;
    private final String email;
    private  String fullName;
    private  Map<String, String > roleInProject;
    private  String avatarUrl;

    public static Result<UserEntity, DomainException> createUserFromAccount (accountEntity account){
        try{

            return Result.success(
                    UserEntity.builder()
                            .id(account.getAccountID())
                            .avatarUrl("")
                            .fullName("")
                            .roleInProject(new HashMap<>())
                            .build()
            );
        }
        catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"DOMAIN_EXCEPTION"));
        }
    }
}
