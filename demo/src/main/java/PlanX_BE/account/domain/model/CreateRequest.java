package PlanX_BE.account.domain.model;

import lombok.Data;

@Data
public class CreateRequest {
    private String email;
    private String password;
    private ACCOUNT_ROLE role;
}
