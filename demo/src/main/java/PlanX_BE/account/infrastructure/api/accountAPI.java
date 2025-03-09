package PlanX_BE.account.infrastructure.api;

import PlanX_BE.account.domain.model.LoginRequest;
import PlanX_BE.account.domain.service.accountService;
import PlanX_BE.account.infrastructure.repository_implement.accountEntity;
import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.exception.ValidateException;
import PlanX_BE.share.domain.model.Result;
import PlanX_BE.share.infrastructure.api.ResponseBody;
import PlanX_BE.share.infrastructure.api.ResponseBodyFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/account")
@AllArgsConstructor
public class accountAPI {

        @Autowired
        private final accountService accountService;

        @GetMapping("/login")
        public ResponseEntity<ResponseBody> login(@RequestBody LoginRequest request){
            try{
                Result<accountEntity, DomainException> result = accountService.login(request);
                if(result.isFailed()){
                    return ResponseBodyFactory.badRequest(new ValidateException("Thông tin đăng nhập không đúng"));
                }
                else {
                    return ResponseBodyFactory.ok(result.getSuccessData());
                }
            }
            catch (Exception e){
                return ResponseBodyFactory.internalError(new DomainException("Lỗi hệ thống","INTERNAL_ERROR"));
            }
        }


}
