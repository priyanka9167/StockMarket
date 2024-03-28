package org.example.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class SignUpRequest {

    private String email;
    private String password;
    private String name;

}
