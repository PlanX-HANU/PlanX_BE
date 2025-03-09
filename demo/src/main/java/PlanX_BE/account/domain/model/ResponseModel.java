package PlanX_BE.account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseModel {
    private final String refreshToken;
    private final String firstAccessToken;
}
