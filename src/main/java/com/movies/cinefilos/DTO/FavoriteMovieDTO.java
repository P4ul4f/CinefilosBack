package com.movies.cinefilos.DTO;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FavoriteMovieDTO {

    private Long userId;
    private Long movieId;
    private String posterPath;

}
