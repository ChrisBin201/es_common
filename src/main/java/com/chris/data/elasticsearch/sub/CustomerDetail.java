package com.chris.data.elasticsearch.sub;

import com.chris.data.dto.user.CustomerDTO;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetail {
    private long id;
    private String username;
    private String avatar;

    public static CustomerDetail from (CustomerDTO customerDTO) {
        return CustomerDetail.builder()
                .id(customerDTO.getId())
                .username(customerDTO.getUsername())
                .avatar(customerDTO.getAvatar())
                .build();
    }
}
