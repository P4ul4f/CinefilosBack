package com.movies.cinefilos.Service;

import com.movies.cinefilos.DTO.AuthCreateRoleRequest;
import com.movies.cinefilos.DTO.AuthCreateUserRequest;
import com.movies.cinefilos.DTO.AuthLoginRequest;
import com.movies.cinefilos.DTO.AuthResponse;
import com.movies.cinefilos.Entities.Role;
import com.movies.cinefilos.Entities.RoleEnum;
import com.movies.cinefilos.Entities.User;
import com.movies.cinefilos.Repositories.RoleRepository;
import com.movies.cinefilos.Repositories.UserRepository;
import com.movies.cinefilos.Util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario"+username+"no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        user.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.isEnabled(), user.isAccountNonLocked(),
                user.isCredentialsNonExpired(), user.isAccountNonExpired(), authorityList);
    }

    public Long getUserIdFromUsername(String username) {
        // Obtener el usuario desde el repositorio
        Optional<User> userOptional = userRepository.findUserByUsername(username);

        // Verificar si el usuario existe en el Optional
        if (userOptional.isPresent()) {
            User user = userOptional.get(); // Obtener el usuario del Optional
            return user.getId(); // Devolver el ID del usuario
        } else {
            return null; // Si no se encuentra el usuario, devolver null
        }
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();


        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario"+username+"no existe."));
        Long userId = user.getId();

        AuthResponse authResponse = new AuthResponse(username, "User loged successfuly", accessToken, true, userId);
        return authResponse;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        if (userDetails == null){
            throw new BadCredentialsException("Username or password is incorrect");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Password is incorrect");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();

        AuthCreateRoleRequest roleRequest = authCreateUserRequest.roleRequest();
        List<String> roleNames;
        if (roleRequest != null && roleRequest.roleListName() != null && !roleRequest.roleListName().isEmpty()) {
            roleNames = roleRequest.roleListName();
        } else {
            roleNames = Collections.singletonList(RoleEnum.USER.name()); // Asignar por defecto el rol USER
        }

        // Convertir nombres de roles a RoleEnum
        List<RoleEnum> roleEnums = roleNames.stream()
                .map(RoleEnum::valueOf) // Convertir String a RoleEnum
                .collect(Collectors.toList());

        // Buscar los roles correspondientes en la base de datos
        List<Role> roles = roleRepository.findRolesByRoleEnumIn(roleEnums);

        // Si la lista de roles está vacía, asignar el rol por defecto USER
        if (roles.isEmpty()) {
            Role defaultRole = roleRepository.findRoleByRoleEnum(RoleEnum.USER)
                    .orElseThrow(() -> new IllegalStateException("Default USER role not found"));
            roles = Collections.singletonList(defaultRole);
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(new HashSet<>(roles))
                .isEnabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .registrationDate(LocalDate.now())
                .desactivationDate(null)
                .build();

        User userCreated = userRepository.save(user);

        Long userId = userCreated.getId();

        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userCreated.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userCreated.getRoles().stream().flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(), userCreated.getPassword(), authorityList);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(username, "User created successfully", accessToken, true, userId);
        return authResponse;
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    // Método para cambiar la contraseña del usuario
    public void changePassword(String username, String newPassword) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Encriptar la nueva contraseña
        user.setPassword(passwordEncoder.encode(newPassword));

        // Guardar el usuario actualizado en la base de datos
        userRepository.save(user);
    }
}
