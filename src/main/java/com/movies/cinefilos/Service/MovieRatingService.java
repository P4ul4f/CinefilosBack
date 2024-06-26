package com.movies.cinefilos.Service;

import com.movies.cinefilos.DTO.FavoriteMovieDTO;
import com.movies.cinefilos.DTO.MovieRatingDTO;
import com.movies.cinefilos.DTO.TopRatedMovieDTO;
import com.movies.cinefilos.Entities.FavoriteMovie;
import com.movies.cinefilos.Entities.MovieRating;
import com.movies.cinefilos.Repositories.MovieRatingRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                movieRating.setRatingDate(LocalDate.now());
                return movieRatingRepository.save(movieRating);
            } else {
                // Si no existe, crea una nueva calificación
                MovieRating newRating = new MovieRating();
                newRating.setUserId(ratingDTO.getUserId());
                newRating.setMovieId(ratingDTO.getMovieId());
                newRating.setLiked(ratingDTO.isLiked());
                newRating.setRatingDate(LocalDate.now());
                return movieRatingRepository.save(newRating);
            }
        } catch (Exception e) {
            // Captura cualquier excepción y maneja el error
            throw new RuntimeException("Error al guardar la calificación: " + e.getMessage(), e);
        }
    }

    public MovieRatingDTO getMovieRatingForUser(Long movieId, Long userId) {
        Optional<MovieRating> existingRating = movieRatingRepository.findByUserIdAndMovieId(userId, movieId);

        if (existingRating.isPresent()) {
            MovieRating rating = existingRating.get();
            return new MovieRatingDTO(rating.getUserId(), rating.getMovieId(), rating.getLiked());
        } else {
            return null; // O cualquier otro manejo que prefieras cuando no hay calificación del usuario
        }
    }

    public List<Long> getAllTopRatedMovies() {
        List<Object[]> results = movieRatingRepository.findTopRatedMovies();

        // Extraer los IDs de las películas de los resultados
        List<Long> movieIds = results.stream()
                .map(result -> (Long) result[0])
                .collect(Collectors.toList());

        return movieIds;
    }

}

