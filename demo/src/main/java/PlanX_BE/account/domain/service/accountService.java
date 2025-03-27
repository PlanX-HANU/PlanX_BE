package PlanX_BE.account.domain.service;

import PlanX_BE.account.domain.model.ACCOUNT_ROLE;
import PlanX_BE.account.domain.model.LoginRequest;
import PlanX_BE.account.domain.model.ResponseModel;
import PlanX_BE.account.domain.repository_interface.accountRepositoryInterface;
import PlanX_BE.account.infrastructure.repository_implement.accountEntity;
import PlanX_BE.redis.domain.service.RedisService;
import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.exception.NotFoundException;
import PlanX_BE.share.domain.exception.ValidateException;
import PlanX_BE.share.domain.model.Result;
import PlanX_BE.share.domain.service.EncryptionService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class accountService {



    @Autowired
    private final accountRepositoryInterface repositoryInterface;

    @Autowired
    private final EncryptionService encryptionService;

    @Autowired
    private final jwtService jwtService;

    public Result<Boolean, DomainException> isPermitted(ACCOUNT_ROLE role){
        try{
            if(role== ACCOUNT_ROLE.AMDIN || role == ACCOUNT_ROLE.HEAD_OF_MARKETING){
                return Result.success(Boolean.TRUE);
            }
            else {
                return Result.failed(new DomainException("Không có quyền","UNAUTHOURIZED"));
            }
        }catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
        }
    }

    public Result<accountEntity, ValidateException> createNewAccount(ACCOUNT_ROLE role ,accountEntity accountEntity){
        try{
            if(isPermitted(role).isFailed()){
                return Result.failed(new ValidateException("Không có quyền"));
            }
            Result<accountEntity,DomainException> result = repositoryInterface.createNewAccount(accountEntity);
            if(result.isFailed()){
                return Result.failed(new ValidateException("Lỗi tạo tài khoản"));
            }
            else{
                return Result.success(result.getSuccessData());
            }
        }
        catch (Exception e){
            return Result.failed(new ValidateException("Lỗi tạo tài khoản"));
        }
    }


    public Result<accountEntity, ValidateException> deleteAccount(ACCOUNT_ROLE role , String email) {
        try {
            if (isPermitted(role).isFailed()) {
                return Result.failed(new ValidateException("Không có quyền"));
            }
            Result<accountEntity, DomainException> result = repositoryInterface.deleteAccount(email);
            if (result.isFailed()) {
                return Result.failed(new ValidateException("Lỗi xóa tài khoản"));
            } else {
                return Result.success(result.getSuccessData());
            }
        } catch (Exception e) {
            return Result.failed(new ValidateException("Lỗi xóa tài khoản"));
        }
    }


        public Result<accountEntity, NotFoundException> getAccountByEmail(String email){
            try {
                Result<accountEntity, NotFoundException> result = repositoryInterface.getAccountByEmail(email);
                if(result.isFailed()){
                    return Result.failed(result.getFailedData());
                }
                else {

                    return Result.success(result.getSuccessData());
                }
            }
            catch (Exception e) {
                return Result.failed(new NotFoundException("INTERNAL_ERROR"));
            }

        }

        public Result<String,NotFoundException> forgetPassword(String email ){
            try{
                Result<accountEntity,NotFoundException> result = getAccountByEmail(email);
                if(result.isFailed()){
                    return Result.failed(result.getFailedData());
                }
                else{
                    Result<String ,DomainException> newPassword = encryptionService.encryption(result.getSuccessData().getPassword());
                    result.getSuccessData().setPassword(newPassword.getSuccessData());
                    Result<accountEntity,NotFoundException> changingPassword = repositoryInterface.changePassword(email, newPassword.getSuccessData());
                    if(changingPassword.isFailed()){
                        return Result.failed(new NotFoundException(""));
                    }
                    else{
                        return Result.success(newPassword.getSuccessData());
                    }
                }
            } catch (Exception e) {
                return Result.failed(new NotFoundException(""));
            }

        }


        public Result<ResponseModel, DomainException> login(LoginRequest request){
            try{
                Result<accountEntity,NotFoundException> getAccountResult = getAccountByEmail(request.getEmail());
                if(getAccountResult.isFailed()){
                    return Result.failed(new DomainException("Không tìm thấy user","NOT_FOUND"));
                }
                else{
                    Result<Boolean, ValidateException> passwordResult =  encryptionService.checkValidate(request.getPassword(),getAccountResult.getSuccessData().getPassword());
                    if(passwordResult.isFailed()){
                        return Result.failed(new DomainException("Sai mật khẩu","BAD_REQUEST"));
                    }
                    else{
                        Result<ResponseModel,DomainException> responseModel = jwtService.createResponseModel(getAccountResult.getSuccessData());
                        return Result.success(responseModel.getSuccessData());
                    }
                }
            }
            catch (Exception e){
                return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
            }


        }

    }








