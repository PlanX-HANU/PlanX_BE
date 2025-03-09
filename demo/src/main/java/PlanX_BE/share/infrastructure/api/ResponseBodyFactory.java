package PlanX_BE.share.infrastructure.api;



import PlanX_BE.share.domain.exception.NotFoundException;
import PlanX_BE.share.domain.exception.UnauthorizedException;
import PlanX_BE.share.domain.exception.ValidateException;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
@NoArgsConstructor
public class ResponseBodyFactory {
    public static ResponseEntity<ResponseBody> unauthorized(UnauthorizedException exception){
        return ResponseEntity.
                status(401)
                .body(new ResponseBody(
                        "Bạn không có quyền sử dụng chức năng này: "+ exception.getMessage(),
                        "UNAUTHORIZED",
                        null
                ));
    }
    public static ResponseEntity<ResponseBody> ok(Object data){
        return ResponseEntity.
                status(200)
                .body(new ResponseBody(
                        "Thành công",
                        "OK",
                        data
                ));
    }
    public static ResponseEntity<ResponseBody> badRequest(ValidateException exception){
        return ResponseEntity.
                status(400)
                .body(new ResponseBody(
                        "Đã có lỗi xảy ra"+exception.getMessage(),
                        "BAD_REQUEST",
                        null
                ));
    }

    public static ResponseEntity<ResponseBody> notFound(NotFoundException exception){
        return ResponseEntity.
                status(404)
                .body(new ResponseBody(
                        "Không tìm thấy tài nguyên :"+ exception.getMessage(),
                        "NOT_FOUND",
                        null
                ));
    }

    public static ResponseEntity<ResponseBody> internalError(Exception exception){
        return ResponseEntity.
                status(500)
                .body(new ResponseBody(
                        "lỗi hệ thống "+ exception.getMessage(),
                        "SERVER_ERROR",
                        null
                ));
    }
}