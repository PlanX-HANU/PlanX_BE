package PlanX_BE.user.domain.service;

import PlanX_BE.account.infrastructure.repository_implement.accountEntity;
import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.exception.NotFoundException;
import PlanX_BE.share.domain.model.Result;
import PlanX_BE.user.domain.repository_interface.UserRepositoryInterface;
import PlanX_BE.user.infrastructure.repository_implement.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepositoryInterface userRepositoryInterface;

    public Result<UserEntity, DomainException> createNewUser(accountEntity account){
        try{
            Result<UserEntity, DomainException> result = userRepositoryInterface.createUser(account);
            if(result.isFailed()){
                return Result.failed(new DomainException(result.getFailedData().toString(),"INTERNAL_ERROR"));
            }
            else{
                return Result.success(result.getSuccessData());
            }
        }
        catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
        }
    }


    public Result<UserEntity,NotFoundException> deleteAccount(String id){
        try{
            Result<UserEntity,NotFoundException> result = userRepositoryInterface.deleteUser(id);
            if(result.isFailed()){
                return Result.failed(new NotFoundException(result.getFailedData().toString()));
            }
            else{
                return Result.success(result.getSuccessData());
            }
        }
        catch (Exception e){
            return Result.failed(new NotFoundException(e.getMessage()));
        }
    }

    public Result<List<UserEntity>, DomainException> getAllUser(){
        try{
            Result<List<UserEntity>, DomainException> userList = userRepositoryInterface.getAllUser();
            if(userList.isFailed()){
                return Result.failed(new DomainException(userList.getFailedData().toString(),"INTERNAL_ERROR"));

            }
            else {
                return Result.success(userList.getSuccessData());
            }
        }catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
        }
    }

}
