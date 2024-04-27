package com.nisum.service.apirestusers.service.impl;

import com.nisum.service.apirestusers.domain.dto.configuration.GlobalConfigurationDto;
import com.nisum.service.apirestusers.domain.dto.configuration.GlobalConfigurationMapper;
import com.nisum.service.apirestusers.domain.service.GlobalConfigurationServiceImpl;
import com.nisum.service.apirestusers.infrastucture.enums.GlobalConfigurationEnum;
import com.nisum.service.apirestusers.domain.model.GlobalConfiguration;
import com.nisum.service.apirestusers.infrastucture.repository.GlobalConfigurationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class GlobalConfigurationServiceImplTest {
    @Mock
    private GlobalConfigurationRepository globalConfigurationRepository;
    @Mock
    private GlobalConfigurationMapper globalConfigurationMapper;
    @InjectMocks
    private GlobalConfigurationServiceImpl globalConfigurationService;

    @Test
    void createGlobalConfigurationSuccess() {
        GlobalConfigurationDto globalRequest = getGlobalConfigurationDtoData();
        Mockito.when(globalConfigurationRepository.findByName(Mockito.any()))
                .thenReturn(Optional.empty());
        Mockito.when(globalConfigurationMapper.toModel(Mockito.any()))
                .thenReturn(getGlobalConfigurationData());
        Mockito.when(globalConfigurationMapper.toDto(Mockito.any()))
                .thenReturn(getGlobalConfigurationDtoData());
        GlobalConfigurationDto result = globalConfigurationService.createGlobalConfiguration(getGlobalConfigurationDtoData());
        Mockito.verify(globalConfigurationRepository, Mockito.times(1))
                .findByName(GlobalConfigurationEnum.EMAIL_REGULAR_EXPRESSION);
        Assertions.assertNotEquals(null, result);
        Assertions.assertEquals(result.getName(), globalRequest.getName());
        Assertions.assertEquals(result.getPattern(), globalRequest.getPattern());
        Assertions.assertEquals(result.getDescription(), globalRequest.getDescription());
    }

    @Test
    void testCreateGlobalConfigurationFailure() {
        Mockito.when(globalConfigurationRepository.findByName(Mockito.any())).thenReturn(Optional.of(getGlobalConfigurationData()));

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            globalConfigurationService.createGlobalConfiguration(getGlobalConfigurationDtoData());
        });

        Mockito.verify(globalConfigurationRepository).findByName(Mockito.any());
        Mockito.verifyNoMoreInteractions(globalConfigurationRepository, globalConfigurationMapper);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        Assertions.assertEquals("Ya existe una Configuración con el mismo nombre", exception.getReason());
    }

    @Test
    void testUpdateGlobalConfigurationSuccess() {
        UUID globalConfigurationId = UUID.randomUUID();
        GlobalConfigurationDto globalConfigurationDto = getGlobalConfigurationDtoData();
        GlobalConfiguration globalConfiguration = getGlobalConfigurationData();
        Mockito.when(globalConfigurationRepository.findById(globalConfigurationId)).thenReturn(Optional.of(globalConfiguration));
        Mockito.when(globalConfigurationRepository.save(globalConfiguration)).thenReturn(globalConfiguration);
        Mockito.when(globalConfigurationMapper.toDto(globalConfiguration)).thenReturn(globalConfigurationDto);

        GlobalConfigurationDto result = globalConfigurationService.updateGlobalConfiguration(globalConfigurationId, globalConfigurationDto);

        Mockito.verify(globalConfigurationRepository).findById(globalConfigurationId);
        Mockito.verify(globalConfigurationRepository).save(globalConfiguration);
        Mockito.verify(globalConfigurationMapper).toDto(globalConfiguration);
        Assertions.assertEquals(globalConfigurationDto, result);
    }

    @Test
    void testUpdateGlobalConfigurationFailure() {
        UUID globalConfigurationId = UUID.randomUUID();
        GlobalConfigurationDto globalConfigurationDto = getGlobalConfigurationDtoData();
        Mockito.when(globalConfigurationRepository.findById(globalConfigurationId)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            globalConfigurationService.updateGlobalConfiguration(globalConfigurationId, globalConfigurationDto);
        });

        Mockito.verify(globalConfigurationRepository).findById(globalConfigurationId);
        Mockito.verifyNoMoreInteractions(globalConfigurationRepository, globalConfigurationMapper);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Assertions.assertEquals("El registro no existe", exception.getReason());
    }

    @Test
    void testGetPatternByNameSuccess() {
        GlobalConfigurationEnum name = GlobalConfigurationEnum.PASSWORD_REGULAR_EXPRESSION;
        GlobalConfiguration globalConfiguration = getGlobalConfigurationData();
        Mockito.when(globalConfigurationRepository.findByName(name)).thenReturn(Optional.of(globalConfiguration));

        String result = globalConfigurationService.getPatternByName(name);

        Mockito.verify(globalConfigurationRepository).findByName(name);
        Assertions.assertEquals(globalConfiguration.getPattern(), result);
    }

    @Test
    void testGetPatternByNameFailure() {
        GlobalConfigurationEnum name = GlobalConfigurationEnum.PASSWORD_REGULAR_EXPRESSION;
        Mockito.when(globalConfigurationRepository.findByName(name)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            globalConfigurationService.getPatternByName(name);
        });

        Mockito.verify(globalConfigurationRepository).findByName(name);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Assertions.assertEquals("Error: debe configurar el patron: PASSWORD_REGULAR_EXPRESSION", exception.getReason());
    }

    GlobalConfigurationDto getGlobalConfigurationDtoData(){
        return
                GlobalConfigurationDto.builder()
                        .id(UUID.randomUUID())
                        .name(GlobalConfigurationEnum.EMAIL_REGULAR_EXPRESSION)
                        .pattern("este es el pattern")
                        .description("esta es la des")
                        .build();
    }

    GlobalConfiguration getGlobalConfigurationData(){
        return
                GlobalConfiguration.builder()
                        .id(UUID.randomUUID())
                        .name(GlobalConfigurationEnum.EMAIL_REGULAR_EXPRESSION)
                        .pattern("este es el pattern")
                        .description("esta es la des")
                        .build();
    }
}