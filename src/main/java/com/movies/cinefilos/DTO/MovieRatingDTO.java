package com.movies.cinefilos.DTO;

public class MovieRatingDTO {

    public Long userId;
    public Long movieId;
    public Boolean liked;

    public MovieRatingDTO() {
    }

    public MovieRatingDTO(Long userId, Long movieId, Boolean liked) {
        this.userId = userId;
        this.movieId = movieId;
        this.liked = liked;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Boolean isLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
