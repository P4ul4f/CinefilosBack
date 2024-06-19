package com.movies.cinefilos.Service;

import com.movies.cinefilos.DTO.FavoriteMovieDTO;
import com.movies.cinefilos.Entities.FavoriteMovie;
import com.movies.cinefilos.Repositories.FavoriteMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FavoriteMovieService {

    private final FavoriteMovieRepository favoriteMovieRepository;

    public FavoriteMovieService(FavoriteMovieRepository favoriteMovieRepository) {
        this.favoriteMovieRepository = favoriteMovieRepository;
    }

    public FavoriteMovie addFavorite(FavoriteMovieDTO favoriteMovieDTO) {
        try {
            // Verificar si ya existe la película en favoritos para este usuario
            Optional<FavoriteMovie> existingFavorite = favoriteMovieRepository.findByUserIdAndMovieId(
                    favoriteMovieDTO.getUserId(), favoriteMovieDTO.getMovieId());

            if (existingFavorite.isPresent()) {
                // Si ya existe, puedes manejarlo según tus requisitos (por ejemplo, actualizar si es necesario)
                return existingFavorite.get();
            } else {
                // Si no existe, crea una nueva entrada en favoritos
                FavoriteMovie newFavorite = new FavoriteMovie();
                newFavorite.setUserId(favoriteMovieDTO.getUserId());
                newFavorite.setMovieId(favoriteMovieDTO.getMovieId());
                newFavorite.setPosterPath(favoriteMovieDTO.getPosterPath());
                return favoriteMovieRepository.save(newFavorite);
            }
        } catch (Exception e) {
            // Manejar excepciones según tus necesidades
            throw new RuntimeException("Error al añadir la película a favoritos: " + e.getMessage(), e);
        }
    }

    public List<FavoriteMovieDTO> getFavoriteMoviesByUserId(Long userId) {
        List<FavoriteMovie> favoriteMovies = favoriteMovieRepository.findByUserId(userId);
        return favoriteMovies.stream()
                .map(movie-> new FavoriteMovieDTO(movie.getUserId(), movie.getMovieId(), movie.getPosterPath()))
                .collect(Collectors.toList());
    }
}
