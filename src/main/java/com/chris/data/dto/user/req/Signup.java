package com.chris.data.dto.user.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import jakarta.validation.constraints.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true)
public abstract class  Signup {
//    protected Long id;
    @NotBlank
//    @Size(min = 3, max = 20)
    protected String fullname;
    @NotBlank
//    @Size(min = 3, max = 20)
    protected String username;
    @NotBlank
//    @Size(min = 6, max = 40)
    protected String password;

//    @Enumerated(EnumType.STRING)
//    protected Role.RoleName role;

    @NotBlank
    @Pattern(regexp="^$|[0-9]{10}")
    protected String phone;
    @Email(message = "Email should be valid")
    protected String email;

}
