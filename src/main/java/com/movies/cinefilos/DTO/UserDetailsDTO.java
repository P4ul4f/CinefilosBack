package com.movies.cinefilos.DTO;

import java.time.LocalDate;

public class UserDetailsDTO {
    private String username;
    private String userRole;
    private LocalDate registrationDate;

    public UserDetailsDTO(String username, String userRole, LocalDate registrationDate) {
        this.username = username;
        this.userRole = userRole;
        this.registrationDate = registrationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}

