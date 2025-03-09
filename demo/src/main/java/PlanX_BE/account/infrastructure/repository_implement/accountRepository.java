package PlanX_BE.account.infrastructure.repository_implement;

import PlanX_BE.account.domain.repository_interface.accountRepositoryInterface;
import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.exception.NotFoundException;
import PlanX_BE.share.domain.exception.ValidateException;
import PlanX_BE.share.domain.model.Result;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class accountRepository implements accountRepositoryInterface {


    @Autowired
    private final MongoTemplate mongoTemplate;


    public Result<accountEntity, NotFoundException> getAccountByEmail(String email){
        try {
            Query query = new Query(Criteria.where("email").is(email));
            accountEntity account = mongoTemplate.findOne(query, accountEntity.class);
            if(account==null){
                return Result.failed(new NotFoundException("Tài khoản không tồn tại"));
            }
            else {
                return Result.success(account);
            }
        }
        catch (Exception e){
            return Result.failed(new NotFoundException(e.getMessage()));
        }
    }


    public Result<accountEntity, DomainException> createNewAccount(accountEntity account){
        try{
            accountEntity accountEntity = mongoTemplate.save(account,"account");
            if(accountEntity.getAccountID()==null){
                return Result.failed(new DomainException("Lỗi tạo tài khoản","INTERNAL_ERROR"));
            }
            else{
                return Result.success(accountEntity);
            }
        }
        catch (Exception e){
            return Result.failed(new DomainException("Lỗi máy chủ","INTERNAL_ERROR"));
        }
    }


    public Result<accountEntity, DomainException> deleteAccount(String email){
        try{
            Query query = new Query(Criteria.where("email").is(email));
            accountEntity account = mongoTemplate.findAndRemove(query, accountEntity.class);
            if(account==null){
                return Result.failed(new DomainException("Lỗi xóa tài khoản","BAD_REQUEST"));
            }
            else {
                return Result.success(account);
            }
        }
        catch (Exception e){
            return Result.failed(new DomainException("Lỗi máy chủ", "INTERNAL_ERROR"));
        }
    }
}
