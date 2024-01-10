package com.chris.data.dto.user;

import com.chris.data.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import com.chris.data.dto.BaseDTO;

@Data
@JsonIgnoreProperties(value = {"created_by", "created_date", "last_modified_by", "last_modified_date"}, allowGetters = true,ignoreUnknown = true)
public class UserDTO extends BaseDTO<String> {
    private long id;
    private String avatar;
    private String username;
    private String fullname;
    private User.UserStatus status;
    @Pattern(regexp="^$|[0-9]{10}")
    private String phone;
    @Email
    private String email;
    @JsonProperty("role_names")
    private List<String> roleNames = new ArrayList<>();

}
