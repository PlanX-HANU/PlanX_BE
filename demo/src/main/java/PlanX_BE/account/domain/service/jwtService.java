package PlanX_BE.account.domain.service;

import PlanX_BE.account.domain.model.ResponseModel;
import PlanX_BE.account.infrastructure.repository_implement.accountEntity;
import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.exception.ValidateException;
import PlanX_BE.share.domain.model.Result;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashMap;


@Service
public class jwtService {

private final String key ="PlanXsudaisdhaisdausidhasiufsdfsdjiaosdjiasdjasiodhasodasoidasoidadasijdaosjaisdjasdasodasasiodasjodjsaiodasodjasoida";

    public Result<String , DomainException> generateToken(double ttlInHours, accountEntity account){
        try{
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("sessionId",account.getAccountID());
            claims.put("role",account.getRole().toString());
            Result<String ,Boolean> token = createToken(claims, ttlInHours);
            if(token.isFailed()){
                return Result.failed(new DomainException("","INTERNAL_ERROR"));
            }
            else {
                return Result.success(token.getSuccessData());
            }
        }
        catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
        }
    }
    private Result<String,Boolean> createToken(HashMap<String,Object> claims, double ttlInHours){
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuer("PlanX")

                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date((long) (System.currentTimeMillis() + 1000 * 60 * 60 * ttlInHours)))
                    .signWith(SignatureAlgorithm.HS384,key)
                    .compact();
            return Result.success(token);
        }
        catch (Exception e){
            return Result.failed(false);
        }
    }
    public Result<String, ValidateException> tokenValidator(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getIssuer() == null || claims.getSubject() == null) {
                return Result.failed(new ValidateException("INVALID_TOKEN"));
            }
            return Result.success(claims.get("role", String.class));
        }
        catch (ExpiredJwtException e) {
            return Result.failed(new ValidateException("TOKEN_TIME_OUT"));
        }
        catch (SignatureException e) {
            return Result.failed(new ValidateException("INVALID_SIGNATURE"));
        }
        catch (Exception e) {
            return Result.failed(new ValidateException("INVALID_TOKEN"));
        }
    }

    public Result<ResponseModel, DomainException> createResponseModel(accountEntity account ){
        try{
            Result<String, DomainException> firstAccessToken = generateToken(0.25,account);
            Result<String, DomainException> refreshToken = generateToken(168, account);
            if(firstAccessToken.isFailed()|| refreshToken.isFailed()){
                return Result.failed(new DomainException("","INTERNAL_ERROR"));
            }
            else{
                ResponseModel responseModel = new ResponseModel(refreshToken.getSuccessData(), firstAccessToken.getSuccessData());
                return Result.success(responseModel);
            }
        }
        catch (Exception e){
            return Result.failed(new DomainException("","INTERNAL_ERROR"));
        }
    }





//    public Result<String, ValidateException> extractFromToken(String token , String subject){
//        try{
//            Claims claims = Jwts.parser()
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            String sessionID = claims.get("sessionId" , String.class);
//            ACCOUNT_ROLE role =claims.get("role", ACCOUNT_ROLE.class);
//            if(subject.equals("sessionID")){
//                return Result.success(sessionID);
//            } else if (subject.equals("role")) {
//                return Result.success(role.toString());
//            }
//            else {
//                return Result.failed(new ValidateException("NO_IDENTIFIER"));
//            }
//        }
//        catch (Exception e){
//            return Result.failed(new ValidateException("INVALID_TOKEN"));
//        }
//    }
}
