package com.chris.data.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;


public interface BaseMapper<Model,DTO> {
    @Mapping(target = "id",source = "id",ignore = true)
    Model toModel(DTO dto);
    DTO toDto(Model model);
    List<Model> toModel(List<DTO> dtos);
    List<DTO> toDto(List<Model> models);
    Set<Model> toModel(Set<DTO> dtos);
    Set<DTO> toDto(Set<Model> models);

    @Mapping(target = "id", ignore = true)
    Model updateModel(DTO dto,@MappingTarget Model model);

}
