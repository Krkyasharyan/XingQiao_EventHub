package com.example.api.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    private Integer userId;
    private String username;
    private Boolean isAdmin;
}

