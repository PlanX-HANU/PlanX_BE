package PlanX_BE.redis.domain.service;

import PlanX_BE.share.domain.exception.DomainException;
import PlanX_BE.share.domain.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Result<Object, DomainException> saveObject(Object object, String redisKey){
        try{
             redisTemplate.opsForValue().set(redisKey,object);
             return Result.success(object);
        }
        catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
        }
    }

    public Result<Object,DomainException> retrieveObject(String redisKey){
        try{
            Object object =  redisTemplate.opsForValue().get(redisKey);
            if(object==null){
                return Result.failed(new DomainException("Không tìm thấy session","NOT_FOUND"));

            }
            return Result.success(object);
        }
        catch (Exception e){
            return Result.failed(new DomainException(e.getMessage(),"INTERNAL_ERROR"));
        }
    }

}
