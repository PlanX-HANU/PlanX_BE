package PlanX_BE.share.infrastructure.api;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ResponseBody {
    public final String message;
    public final String code;
    public final Object data;

}
