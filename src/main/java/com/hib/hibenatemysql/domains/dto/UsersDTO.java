package com.hib.hibenatemysql.domains.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String userId;
    private String password;
    private String token;
}
