package com.chris.data.mapper.product;

import com.chris.data.dto.product.PromotionDTO;
import com.chris.data.entity.product.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.chris.data.mapper.BaseMapper;

@Mapper
public interface PromotionMapper extends BaseMapper<Promotion, PromotionDTO>{
    PromotionMapper INSTANCE = Mappers.getMapper( PromotionMapper.class );

}
