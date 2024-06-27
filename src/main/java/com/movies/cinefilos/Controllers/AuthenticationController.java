package com.movies.cinefilos.Controllers;

import com.movies.cinefilos.DTO.*;
import com.movies.cinefilos.Entities.FavoriteMovie;
import com.movies.cinefilos.Entities.MovieRating;
import com.movies.cinefilos.Entities.User;
import com.movies.cinefilos.Repositories.MovieRatingRepository;
import com.movies.cinefilos.Repositories.UserRepository;
import com.movies.cinefilos.Service.FavoriteMovieService;
import com.movies.cinefilos.Service.MovieRatingService;
import com.movies.cinefilos.Service.UserDetailServiceImpl;
import com.movies.cinefilos.Util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MovieRatingService movieRatingService;

    @Autowired
    private FavoriteMovieService favoriteMovieService;
    @Autowired
    private MovieRatingRepository movieRatingRepository;

    //LOGIN
    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        try {
            AuthResponse authResponse = this.userDetailServiceImpl.loginUser(userRequest);
            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException ex) {
            // Manejo de errores de autenticación (credenciales incorrectas, usuario no válido, etc.)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception ex) {
            // Otros errores internos del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //REGISTER
    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody AuthCreateUserRequest authCreateUser) {
        try {
            AuthResponse response = this.userDetailServiceImpl.createUser(authCreateUser);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            // Manejo genérico para cualquier excepción
            String errorMessage = ex.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    // RESET PASSWORD
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            // Validar que el usuario existe y verificar la contraseña actual
            String username = resetPasswordRequest.getUsername();
            String currentPassword = resetPasswordRequest.getCurrentPassword();
            String newPassword = resetPasswordRequest.getNewPassword();

            AuthLoginRequest loginRequest = new AuthLoginRequest(username, currentPassword);
            Authentication authentication = userDetailServiceImpl.authenticate(username, currentPassword);
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }

            // Cambiar la contraseña
            userDetailServiceImpl.changePassword(username, newPassword);

            return ResponseEntity.ok("Contraseña cambiada exitosamente");

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cambiar la contraseña");
        }
    }

    //Obtener los datos del usuario por su id
    @GetMapping("/details")
    public ResponseEntity<Object> getUserDetails() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Simulación de obtención de datos del usuario
            Long userId = 1L;  // Obtén el ID del usuario según tu lógica
            String userRole = "USER";  // Obtén el rol del usuario según tu lógica
            LocalDate registrationDate = LocalDate.now();  // Obtén la fecha de registro del usuario según tu lógica

            // Construye el DTO con los detalles del usuario
            UserDetailsDTO userDetailsDTO = new UserDetailsDTO(username, userRole, registrationDate);

            return ResponseEntity.ok(userDetailsDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //calificar pelicula
    @PostMapping("/rate")
    public ResponseEntity<?> rateMovie(@RequestBody MovieRatingDTO ratingDTO) {
        try {
            movieRatingService.rateMovie(ratingDTO);
            return ResponseEntity.ok("Película calificada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la calificación");
        }
    }

    //get a calificacion del usuario a una pelicula
    @GetMapping("/movie/{movieId}/rating/{userId}")
    public ResponseEntity<MovieRatingDTO> getMovieRatingForUser(@PathVariable Long movieId, @PathVariable Long userId) {
        try {
            MovieRatingDTO userRating = movieRatingService.getMovieRatingForUser(movieId, userId);
            return ResponseEntity.ok(userRating);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //agregar peliculas a la lista
    @PostMapping("/add-favorite")
    public ResponseEntity<?> addFavoriteMovie(@RequestBody FavoriteMovieDTO favoriteMovieDTO) {
        try {
            FavoriteMovie savedFavorite = favoriteMovieService.addFavorite(favoriteMovieDTO);
            return ResponseEntity.ok(savedFavorite);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la película en favoritos");
        }
    }

    //Get a peliculas en la lista del usuario
    @GetMapping("/favorites/{userId}")
    public ResponseEntity<List<FavoriteMovieDTO>> getFavoriteMovies(@PathVariable Long userId) {
        try {
            List<FavoriteMovieDTO> favoriteMovies = favoriteMovieService.getFavoriteMoviesByUserId(userId);
            return ResponseEntity.ok(favoriteMovies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

   //obtener peliculas mas votadas

    @GetMapping("/top-rated")
    public ResponseEntity<List<Long>> getTopRatedMovies() {
        List<Long> topRatedMovies = movieRatingService.getAllTopRatedMovies(); // Suponiendo que hay un método para obtener todas las películas mejor valoradas
        return ResponseEntity.ok(topRatedMovies);
    }


}
