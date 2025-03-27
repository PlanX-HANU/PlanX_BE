package PlanX_BE.account.domain.service;

import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.model.Result;
import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class mailService {
    private final String key = "re_2fabtn39_CKwm6cocR6wzYPQDaKwj7bmi";

    private final Resend resend = new Resend(key);

    public Result<Boolean, DomainException> sendEmail(String content){
        try{
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("Acme <onboarding@resend.dev>")
                    .to("gdschanu.phongdv2909@gmail.com")
                    .subject("Reset Password")
                    .html("<strong>"+content+"</strong>")
                    .build();
            CreateEmailResponse data =resend.emails().send(params);
            return Result.success(true);
        }
        catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
        }
    }



}
