package com.movies.cinefilos.Repositories;

import com.movies.cinefilos.Entities.MovieRating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {

    Optional<MovieRating> findByUserIdAndMovieId(Long userId, Long movieId);

    @Query("SELECT mr.movieId, COUNT(mr.movieId) as voteCount FROM MovieRating mr WHERE mr.liked = TRUE GROUP BY mr.movieId ORDER BY voteCount DESC")
    List<Object[]> findTopRatedMovies(); // Eliminar Pageable para obtener todas las películas
}
