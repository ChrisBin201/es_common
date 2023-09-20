package com.chris.data.mapper.product;

import com.chris.data.dto.product.VariationOptionDTO;
import com.chris.data.entity.product.VariationOption;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.chris.data.mapper.BaseMapper;

@Mapper
public interface VariationOptionMapper extends BaseMapper<VariationOption, VariationOptionDTO>{
    VariationOptionMapper INSTANCE = Mappers.getMapper( VariationOptionMapper.class );

    @Override
    VariationOption toModel(VariationOptionDTO dto);

}
