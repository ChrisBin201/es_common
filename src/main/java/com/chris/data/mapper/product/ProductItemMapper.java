package com.chris.data.mapper.product;

import com.chris.data.dto.product.ProductItemDTO;
import com.chris.data.entity.product.ProductItem;
import com.chris.data.entity.product.VariationOption;
import com.chris.data.entity.product.sub.VariantOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import com.chris.data.mapper.BaseMapper;


import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ProductItemMapper extends BaseMapper<ProductItem, ProductItemDTO>{
    ProductItemMapper INSTANCE = Mappers.getMapper( ProductItemMapper.class );

    @Override
    @Mapping(target = "id",source = "id",ignore = true)
    @Mapping(target = "preview",source = "preview",ignore = true)
    @Mapping(target = "variantOptions",source = "variantOptionCodes",ignore = true)
    ProductItem toModel(ProductItemDTO dto);

    @Override
    @Mapping(target = "preview", source = "preview", ignore = true)
    @Mapping(target = "variantOptionCodes",source = "variantOptions",qualifiedByName = "varOptionToCode")
    ProductItemDTO toDto(ProductItem model);

    @Override
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "preview",ignore = true)
    @Mapping(target = "SKU",ignore = true)
    ProductItem updateModel(ProductItemDTO productItemDTO,@MappingTarget ProductItem productItem);

    @Named("varOptionToCode")
    public static List<String> varOptionToCode(List<VariantOption> variationOptions) {
        return variationOptions.stream()
                .map(vo -> vo.getCode())
                .collect(Collectors.toList());
    }

}
