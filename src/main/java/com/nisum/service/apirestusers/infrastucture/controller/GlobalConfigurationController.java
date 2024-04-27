package com.nisum.service.apirestusers.infrastucture.controller;

import com.nisum.service.apirestusers.domain.dto.configuration.GlobalConfigurationDto;
import com.nisum.service.apirestusers.application.GlobalConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/global/configuration")
@RequiredArgsConstructor
public class GlobalConfigurationController {

    private final GlobalConfigurationService globalConfigurationService;

    @GetMapping
    public List<GlobalConfigurationDto> getAllGlobalConfiguration(){
        return globalConfigurationService.getAllGlobalConfigurations();
    }

    @Operation(summary = "Crea configuración", description = "Recibe la data UserDto, creando la configuración global siempre y cuando no se repita el nombre")
    @PostMapping("/create")
    public GlobalConfigurationDto createGlobalConfiguration(@Valid @RequestBody GlobalConfigurationDto globalConfigurationDto){
        return globalConfigurationService.createGlobalConfiguration(globalConfigurationDto);
    }

    @Operation(summary = "Actualiza configuración", description = "La configuración se actualiza a partir de su Id")
    @PutMapping("/{globalConfigurationId}")
    public GlobalConfigurationDto updateGlobalConfiguration(@Valid @PathVariable UUID globalConfigurationId, @RequestBody GlobalConfigurationDto globalConfigurationDto) {
        return globalConfigurationService.updateGlobalConfiguration(globalConfigurationId, globalConfigurationDto);
    }
}
