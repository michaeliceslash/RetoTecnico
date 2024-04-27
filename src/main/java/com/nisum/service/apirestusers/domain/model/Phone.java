package com.nisum.service.apirestusers.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "phones")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private String number;

    @NotNull
    @Column(nullable = false)
    private String cityCode;

    @NotNull
    @Column(nullable = false)
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
