package com.movies.cinefilos.Repositories;

import com.movies.cinefilos.Entities.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {

    Optional<MovieRating> findByUserIdAndMovieId(Long userId, Long movieId);
}
