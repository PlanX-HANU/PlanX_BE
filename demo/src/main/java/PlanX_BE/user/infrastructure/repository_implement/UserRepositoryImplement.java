package PlanX_BE.user.infrastructure.repository_implement;

import PlanX_BE.account.infrastructure.repository_implement.accountEntity;
import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.exception.NotFoundException;
import PlanX_BE.share.domain.model.Result;
import PlanX_BE.user.domain.repository_interface.UserRepositoryInterface;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserRepositoryImplement implements UserRepositoryInterface {
    @Autowired
    private final MongoTemplate mongoTemplate;

    public Result<List<UserEntity>, DomainException> getAllUser(){
        try {
            return Result.success(mongoTemplate.findAll(UserEntity.class));
        }
        catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
        }
    }


    public Result<UserEntity, NotFoundException> deleteUser(String id){
        try {
            Query query = new Query(Criteria.where("id").is(id));
            UserEntity user = mongoTemplate.findAndRemove(query, UserEntity.class);
            return Result.success(user);
        }
        catch (Exception e){
            return Result.failed(new NotFoundException(new NotFoundException(e.getMessage())));
        }
    }

    public Result<UserEntity,DomainException> createUser(accountEntity account){
        try{
            Result<UserEntity,DomainException> user = UserEntity.createUserFromAccount(account);
            if(user.isFailed()) {
                return Result.failed(new DomainException(user.getFailedData().toString(),"INTERNAL_ERROR"));
            }
            else {
                UserEntity newUser = mongoTemplate.insert(user.getSuccessData());
                return Result.success(newUser);
            }
        }
        catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
        }
    }


}
