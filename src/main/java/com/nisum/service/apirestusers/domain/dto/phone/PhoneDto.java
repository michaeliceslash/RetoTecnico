package com.nisum.service.apirestusers.domain.dto.phone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nisum.service.apirestusers.domain.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDto {

    private UUID id;

    @NotNull
    private String number;

    @NotNull
    @JsonProperty("citycode")
    private String cityCode;

    @NotNull
    @JsonProperty("contrycode")
    private String countryCode;

    @JsonIgnore
    private User user;

}
