package com.chris.data.mapper.product;

import com.chris.data.dto.product.ProductDTO;
import com.chris.data.entity.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.chris.data.mapper.BaseMapper;

//@Mapper(uses = {VariationMapper.class, ProductItemMapper.class, CategoryMapper.class})
@Mapper(uses = {ProductItemMapper.class, CategoryMapper.class})
public interface ProductMapper extends BaseMapper<Product, ProductDTO> {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);


}
