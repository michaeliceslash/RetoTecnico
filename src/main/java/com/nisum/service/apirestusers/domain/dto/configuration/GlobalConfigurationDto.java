package com.nisum.service.apirestusers.domain.dto.configuration;

import com.nisum.service.apirestusers.infrastucture.enums.GlobalConfigurationEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GlobalConfigurationDto {
    private UUID id;
    @NotNull(message = "Error: name no puede ser nulo")
    private GlobalConfigurationEnum name;
    @NotNull(message = "Error: description no puede ser nulo")
    private String description;
    @NotNull(message = "Error: pattern no puede ser nulo")
    private String pattern;
}
