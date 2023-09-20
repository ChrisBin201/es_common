package com.chris.data.mapper.user;

import com.chris.data.entity.user.Customer;
import com.chris.data.entity.user.Seller;
import com.chris.data.entity.user.User;
import com.chris.data.entity.user.UserRole;
import com.chris.data.dto.user.CustomerDTO;
import com.chris.data.dto.user.SellerDTO;
import com.chris.data.dto.user.UserDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;
import com.chris.data.mapper.BaseMapper;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper extends BaseMapper<User, UserDTO>{
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Override
    @Mapping(source = "userRoles", target = "roleNames", qualifiedByName = "userRoleToRole")
    @SubclassMappings({
            @SubclassMapping(source = Customer.class, target = CustomerDTO.class),
            @SubclassMapping(source = Seller.class, target = SellerDTO.class)
    })
    UserDTO toDto(User model);

    @Override
    @SubclassMappings({
            @SubclassMapping(source = CustomerDTO.class, target = Customer.class),
            @SubclassMapping(source = SellerDTO.class, target = Seller.class)
    })
    @Mapping(source = "roleNames", target = "userRoles", ignore = true)
    User toModel(UserDTO model);

    @Override

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "status",ignore = true)
    @Mapping(target = "userRoles", ignore = true)
    User updateModel(UserDTO dto, @MappingTarget User model);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "status",ignore = true)
    @Mapping(target = "userRoles", ignore = true)
    Seller updateModel(SellerDTO dto, @MappingTarget Seller model);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "status",ignore = true)
    @Mapping(target = "userRoles", ignore = true)
    Customer updateModel(CustomerDTO dto, @MappingTarget Customer model);

    @Named("userRoleToRole")
    public static List<String> userRoleToRole(List<UserRole> userRoles) {
        return userRoles.stream()
                .map(ur -> ur.getRole().getName().name())
                .collect(Collectors.toList());
    }


}
