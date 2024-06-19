package com.movies.cinefilos.Repositories;

import com.movies.cinefilos.Entities.FavoriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {

    Optional<FavoriteMovie> findByUserIdAndMovieId(Long userId, Long movieId);

    List<FavoriteMovie> findByUserId(Long userId);
}
