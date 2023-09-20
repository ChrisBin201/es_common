package com.chris.data.mapper.product;

import com.chris.data.mapper.BaseMapper;
import com.chris.data.dto.product.CategoryDTO;
import com.chris.data.entity.product.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper extends BaseMapper<Category, CategoryDTO> {
    CategoryMapper INSTANCE = Mappers.getMapper( CategoryMapper.class );
}
