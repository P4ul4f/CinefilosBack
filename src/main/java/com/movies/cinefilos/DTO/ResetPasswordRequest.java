package com.movies.cinefilos.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String username;
    private String currentPassword;
    private String newPassword;
}
