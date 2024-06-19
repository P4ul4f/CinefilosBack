package com.movies.cinefilos.Service;

import com.movies.cinefilos.DTO.MovieRatingDTO;
import com.movies.cinefilos.Entities.MovieRating;
import com.movies.cinefilos.Repositories.MovieRatingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieRatingService {

    private final MovieRatingRepository movieRatingRepository;

    public MovieRatingService(MovieRatingRepository movieRatingRepository) {
        this.movieRatingRepository = movieRatingRepository;
    }

    public MovieRating rateMovie(MovieRatingDTO ratingDTO) {
        try {
            // Verifica si ya existe una calificación para este usuario y película
            Optional<MovieRating> existingRating = movieRatingRepository.findByUserIdAndMovieId(ratingDTO.getUserId(), ratingDTO.getMovieId());

            if (existingRating.isPresent()) {
                // Si ya existe una calificación, actualiza el valor de "liked"
                MovieRating movieRating = existingRating.get();
                movieRating.setLiked(ratingDTO.isLiked());
                return movieRatingRepository.save(movieRating);
            } else {
                // Si no existe, crea una nueva calificación
                MovieRating newRating = new MovieRating();
                newRating.setUserId(ratingDTO.getUserId());
                newRating.setMovieId(ratingDTO.getMovieId());
                newRating.setLiked(ratingDTO.isLiked());
                return movieRatingRepository.save(newRating);
            }
        } catch (Exception e) {
            // Captura cualquier excepción y maneja el error
            throw new RuntimeException("Error al guardar la calificación: " + e.getMessage(), e);
        }
    }
}

