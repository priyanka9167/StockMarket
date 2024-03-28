package org.example.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class SignInRequest {
    private String email;
    private String password;

}
