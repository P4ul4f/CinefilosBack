package com.movies.cinefilos.Controllers;

import com.movies.cinefilos.DTO.MovieRatingDTO;
import com.movies.cinefilos.Entities.MovieRating;
import com.movies.cinefilos.Service.MovieRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie-ratings")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieRatingController {

   /* private final MovieRatingService movieRatingService;

    public MovieRatingController(MovieRatingService movieRatingService){
        this.movieRatingService = movieRatingService;
    }

    @PostMapping("/rate")
    public ResponseEntity<?> rateMovie(@RequestBody MovieRatingDTO ratingDTO) {
        try {
            MovieRating savedRating = movieRatingService.rateMovie(ratingDTO);
            return ResponseEntity.ok(savedRating);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la calificación");
        }
    }*/
}
