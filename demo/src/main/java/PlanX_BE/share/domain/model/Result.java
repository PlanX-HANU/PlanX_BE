package PlanX_BE.share.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result<S,F> {
    private S successData;
    private F failedData;
    private boolean isFailed ;



    public static <S,F> Result<S,F> success(S successData){
        return new Result<>(
                successData,
                null,
                false
        );

    }
    public static <S,F> Result<S,F> failed(F failedData){
        return new Result<>(
                null,
                failedData,
                true
        );

    }
    public boolean isFailed(){
        return isFailed;
    }

}
