package PlanX_BE.user.domain.repository_interface;

import PlanX_BE.account.infrastructure.repository_implement.accountEntity;
import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.exception.NotFoundException;
import PlanX_BE.share.domain.model.Result;
import PlanX_BE.user.infrastructure.repository_implement.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepositoryInterface {
    public Result<List<UserEntity>, DomainException> getAllUser();
    public Result<UserEntity, NotFoundException> deleteUser(String id);
    public Result<UserEntity,DomainException> createUser(accountEntity account);
}
