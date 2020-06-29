package helpers;

import models.Movie;

import java.util.List;

public class MovieHelper {
    private static List<Movie> movieList;


    public void setMovieList(List<Movie> _movieList) {
        movieList = _movieList;
    }

    public static Movie getMovieByTitle(String title) {
        Movie expectedMovie = movieList.stream()
                .filter(movie -> title.equals(movie.getTitle()))
                .findAny()
                .orElse(null);
        return expectedMovie;
    }


}
