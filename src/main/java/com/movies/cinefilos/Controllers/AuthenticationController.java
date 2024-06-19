package com.movies.cinefilos.Controllers;

import com.movies.cinefilos.DTO.*;
import com.movies.cinefilos.Entities.FavoriteMovie;
import com.movies.cinefilos.Entities.MovieRating;
import com.movies.cinefilos.Entities.User;
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
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    //LOGIN
    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailServiceImpl.loginUser(userRequest), HttpStatus.OK);
    }

    //REGISTER
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUser){
        return new ResponseEntity<>(this.userDetailServiceImpl.createUser(authCreateUser), HttpStatus.CREATED);
    }

    @PostMapping("/rate")
    public ResponseEntity<?> rateMovie(@RequestBody MovieRatingDTO ratingDTO) {
        try {
            MovieRating savedRating = movieRatingService.rateMovie(ratingDTO);
            return ResponseEntity.ok(savedRating);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la calificación");
        }
    }


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

}
