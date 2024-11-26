package ch.nb.api.service;

import ch.nb.api.business.Movie;
import ch.nb.database.MovieMapper;
import ch.nb.utils.SimpleLogger;

import java.sql.SQLException;
import java.util.Optional;

public class MovieService {

    private static MovieService instance;

    private MovieApiService movieApiService = MovieApiService.getInstance();

    private MovieMapper movieMapper = MovieMapper.getInstance();

    private MovieService() {
    }

    public static MovieService getInstance() {
        if (instance == null) {
            instance = new MovieService();
        }
        return instance;
    }

    public Movie getMovieDetailsBytmdbMovieId(Long tmdbMovieId) {
        try {
            Optional<Movie> movieFromDatabase = movieMapper.selectById(tmdbMovieId);
            if (movieFromDatabase.isPresent()) {
                return movieFromDatabase.get();
            }

            String movieDetails = MovieApiService.getMovieDetails(tmdbMovieId);
            Movie movieFromApi = MovieJsonService.mapJsonToMovie(movieDetails);

            addMovie(movieFromApi);
            return movieFromApi;

        } catch (Exception e) {
            SimpleLogger.error("Failed to get movie details by ID: " + e.getMessage());
            throw new RuntimeException("Failed to get movie details by ID, ID may not exist " + e.getMessage());
        }
    }

    public void addMovie(Movie movie) {
        try {
            movieMapper.insert(movie);
        } catch (SQLException e) {
            SimpleLogger.error("Failed to insert movie: " + e.getMessage());
        }
    }

}
