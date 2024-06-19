package com.movies.cinefilos.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true)
    private String username;

    private String password;
    private LocalDate registrationDate;
    @Column(nullable = true)
    private Date desactivationDate;

    //atributos de seguridad
    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_No_Expired")
    private boolean accountNonExpired;

    @Column(name = "account_No_Locked")
    private boolean accountNonLocked;

    @Column(name = "credential_No_Expired")
    private boolean credentialsNonExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    //Set: lista que no permite repetidos
    private Set<Role> roles = new HashSet<>();

}


