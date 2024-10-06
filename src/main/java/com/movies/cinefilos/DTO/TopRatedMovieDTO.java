package com.movies.cinefilos.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopRatedMovieDTO {
    private Long movieId;
    private Long totalLikes;


    public TopRatedMovieDTO(Long movieId, Long totalLikes, String posterPath) {
        this.movieId = movieId;
        this.totalLikes = totalLikes;

    }

}
