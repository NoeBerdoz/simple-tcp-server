package ch.nb.api.service;


import ch.nb.api.business.Movie;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for mapping JSON data from the Movie API into Movie objects.
 * Provides methods to deserialize JSON strings and convert them into structured Movie objects.
 */
public class MovieJsonService {

    private static JsonObject getJsonObject(String jsonString) throws JsonException {
        return (JsonObject) Jsoner.deserialize(jsonString);
    }

    public static Movie mapJsonToMovie(String jsonString) throws JsonException {

        JsonObject jsonObject = getJsonObject(jsonString);

        Long tmdbId = ((Number) jsonObject.get("id")).longValue();
        Float voteAverage = ((Number) jsonObject.get("vote_average")).floatValue();
        int runtime = ((Number) jsonObject.get("runtime")).intValue();
        LocalDate releaseDate = LocalDate.parse((String) jsonObject.get("release_date"));
        int durationInSeconds = runtime * 3600;

        Map<Integer, String> genresMap = new HashMap<>();
        JsonArray genres = (JsonArray) jsonObject.get("genres");
        for (Object genre : genres) {
            JsonObject genreObject = (JsonObject) genre;
            genresMap.put(((BigDecimal) genreObject.get("id")).intValue(), (String) genreObject.get("name"));
        }

        return new Movie.Builder()
                .withTmdbMovieId(tmdbId)
                .withTitle((String) jsonObject.get("title"))
                .withVoteAverage(voteAverage)
                .withReleaseDate(releaseDate)
                .withDurationInSeconds(durationInSeconds)
                .withOriginalTitle((String) jsonObject.get("original_title"))
                .withOriginalLanguage((String) jsonObject.get("original_language"))
                .withGenresMap(genresMap)
                .withImagePath((String) jsonObject.get("poster_path"))
                .withBudget((BigDecimal) jsonObject.get("budget"))
                .build();

    }
}
