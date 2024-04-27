package com.nisum.service.apirestusers.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nisum.service.apirestusers.domain.dto.phone.PhoneDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @JsonFormat(pattern = "dd-MM-yyyy HH.mm.ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "dd-MM-yyyy HH.mm.ss")
    private LocalDateTime modified;

    @JsonFormat(pattern = "dd-MM-yyyy HH.mm.ss")
    @Builder.Default
    private LocalDateTime lastLogin = LocalDateTime.now();

    @Builder.Default
    private String token = "";

    @Builder.Default
    private Boolean isActive = true;

    private Set<PhoneDto> phones;
}
