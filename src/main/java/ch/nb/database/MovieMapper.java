package ch.nb.database;

import ch.nb.api.business.Movie;
import ch.nb.utils.SimpleLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieMapper implements Mapper<Movie> {

    private static MovieMapper instance;

    public static MovieMapper getInstance() {
        if (instance == null) {
            instance = new MovieMapper();
        }
        return instance;
    }

    private MovieMapper() {
    }

    @Override
    public Optional<Movie> selectById(Long id) {
        String sql = "SELECT * FROM MOVIES WHERE ID = ?";

        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapRowToObject(resultSet));
            }

        } catch (SQLException e) {
            SimpleLogger.error("Failed to select MOVIE by ID: " + e.getMessage());
        }

        return Optional.empty();

    }

    public Optional<Movie> selectByTmdbMovieId(Long tmdbMovieid) {
        String sql = "SELECT * FROM MOVIES WHERE TMDB_MOVIE_ID = ?";

        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, tmdbMovieid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapRowToObject(resultSet));
            }

        } catch (SQLException e) {
            SimpleLogger.error("Failed to select MOVIE by TMDB_MOVIE_ID: " + e.getMessage());
        }

        return Optional.empty();

    }

    @Override
    public List<Movie> selectAll() {
        String query = "SELECT * FROM MOVIES";

        List<Movie> movies = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            Integer countMovies = 0;
            while (resultSet.next()) {
                Movie movie = mapRowToObject(resultSet);
                movies.add(movie);
                countMovies++;
            }
            SimpleLogger.info("[SELECTED] MOVIES COUNT: " + countMovies);

        } catch (SQLException e) {
            SimpleLogger.error("Failed to select all MOVIES: " + e.getMessage());
        }

        return movies;
    }

    @Override
    public void insert(Movie movie) throws SQLException{

        String query = "INSERT INTO MOVIES (TMDB_MOVIE_ID, TITLE, VOTE_AVERAGE, RELEASE_DATE, DURATION_IN_SECONDS, ORIGINAL_TITLE, ORIGINAL_LANGUAGE, IMAGE_PATH, BUDGET) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"ID"});
            StatementHelper.bindStatementParameters(
                    preparedStatement,
                    movie.getTmdbMovieId(),
                    movie.getTitle(),
                    movie.getVoteAverage(),
                    movie.getReleaseDate(),
                    movie.getDurationInSeconds(),
                    movie.getOriginalTitle(),
                    movie.getOriginalLanguage(),
                    movie.getImagePath(),
                    movie.getBudget()
            );

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {

                // Set the new generated ID to the restaurant object
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    String generatedId = generatedKeys.getString(1);
                    movie.setId(Long.valueOf(generatedId));
                    SimpleLogger.info("[DB][INSERTED] MOVIE ID: " + movie.getId());

                    connection.commit();
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            SimpleLogger.error("Failed to insert MOVIE: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Movie mapRowToObject(ResultSet resultSet) throws SQLException {

        return new Movie.Builder()
                .withId(resultSet.getLong("ID"))
                .withTmdbMovieId(resultSet.getLong("TMDB_MOVIE_ID"))
                .withTitle(resultSet.getString("TITLE"))
                .withVoteAverage(resultSet.getFloat("VOTE_AVERAGE"))
                .withReleaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .withDurationInSeconds(resultSet.getInt("DURATION_IN_SECONDS"))
                .withOriginalTitle(resultSet.getString("ORIGINAL_TITLE"))
                .withOriginalLanguage(resultSet.getString("ORIGINAL_LANGUAGE"))
                .withImagePath(resultSet.getString("IMAGE_PATH"))
                .withBudget(resultSet.getBigDecimal("BUDGET"))
                .build();

    }
}
