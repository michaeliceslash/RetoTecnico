package com.nisum.service.apirestusers.domain.dto.configuration;

import com.nisum.service.apirestusers.domain.model.GlobalConfiguration;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface GlobalConfigurationMapper {

    GlobalConfigurationDto toDto(GlobalConfiguration model);

    GlobalConfiguration toModel(GlobalConfigurationDto dto);
}
