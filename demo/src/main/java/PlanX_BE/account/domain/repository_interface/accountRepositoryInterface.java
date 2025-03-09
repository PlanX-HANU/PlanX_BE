package PlanX_BE.account.domain.repository_interface;

import PlanX_BE.account.infrastructure.repository_implement.accountEntity;
import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.exception.NotFoundException;
import PlanX_BE.share.domain.exception.ValidateException;
import PlanX_BE.share.domain.model.Result;

public interface accountRepositoryInterface {

    public Result<accountEntity, NotFoundException> getAccountByEmail(String email);
    public Result<accountEntity, DomainException> createNewAccount(accountEntity account);
    public Result<accountEntity, DomainException> deleteAccount(String email);

}
