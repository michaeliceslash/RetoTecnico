package com.nisum.service.apirestusers.service.impl;

import com.nisum.service.apirestusers.domain.dto.phone.PhoneDto;
import com.nisum.service.apirestusers.domain.dto.phone.PhoneMapper;
import com.nisum.service.apirestusers.domain.dto.user.UserDto;
import com.nisum.service.apirestusers.domain.dto.user.UserMapper;
import com.nisum.service.apirestusers.domain.service.UserServiceImpl;
import com.nisum.service.apirestusers.domain.model.Phone;
import com.nisum.service.apirestusers.domain.model.User;
import com.nisum.service.apirestusers.infrastucture.repository.GlobalConfigurationRepository;
import com.nisum.service.apirestusers.infrastucture.repository.UserRepository;
import com.nisum.service.apirestusers.application.GlobalConfigurationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private GlobalConfigurationRepository globalConfigurationRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PhoneMapper phoneMapper;
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private GlobalConfigurationService globalConfigurationService;

    @Test
    void testShowUser_validUserId_returnsUserDto() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toDto(user)).thenReturn(new UserDto());

        UserDto result = userServiceImpl.showUser(userId);

        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(userMapper).toDto(user);
        Assertions.assertNotNull(result);
    }

    @Test
    void testShowUser_invalidUserId_throwsResponseStatusException() {
        UUID userId = UUID.randomUUID();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            userServiceImpl.showUser(userId);
        });

        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(userMapper, Mockito.never()).toDto(Mockito.any(User.class));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Assertions.assertEquals("Error: No se encontro el usuario con id: " + userId, exception.getReason());
    }

    @Test
    void testUpdateUser_validUserIdAndUserDto_returnsUserDto() {

        UUID userId = UUID.randomUUID();
        UserDto userDto = getUserDto();

        User user = getUser();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toDto(Mockito.any(User.class))).thenReturn(userDto);
        Mockito.when(userServiceImpl.getPatterPassword()).thenReturn("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
        Mockito.when(userServiceImpl.getPatterEmail()).thenReturn("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$");
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto result = userServiceImpl.updateUser(userId, userDto);

        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(userRepository).save(user);
        Mockito.verify(userMapper).toDto(user);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(userDto.getName(), result.getName());
        Assertions.assertEquals(userDto.getEmail(), result.getEmail());
        Assertions.assertEquals(userDto.getPassword(), result.getPassword());
        Assertions.assertEquals(userDto.getPhones(), result.getPhones());
    }

    @Test
    void testUpdateUser_invalidUserId_throwsResponseStatusException() {
        UUID userId = UUID.randomUUID();
        UserDto userDto = getUserDto();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            userServiceImpl.updateUser(userId, userDto);
        });

        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
        Mockito.verify(userMapper, Mockito.never()).toDto(Mockito.any(User.class));
    }

    private PhoneDto getPhoneDto(){
        return PhoneDto.builder()
                .id(UUID.randomUUID())
                .cityCode("01")
                .countryCode("59")
                .number("09999999")
                .build();
    }

    private Phone getPhone(){
        return Phone.builder()
                .id(UUID.randomUUID())
                .cityCode("01")
                .countryCode("59")
                .number("09999999")
                .user(getUser())
                .build();
    }

    private UserDto getUserDto(){
        return UserDto.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .password("Passw0rd")
                .phones(new HashSet<>())
                .build();
    }

    private User getUser(){
        return User.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john.doe@example.com")
                .password("Passw0rd")
                .phones(new HashSet<>())
                .build();
    }
}
