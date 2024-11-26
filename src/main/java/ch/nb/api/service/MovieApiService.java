package ch.nb.api.service;

import ch.nb.utils.PropertiesLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Service class for interacting with the Movie API.
 * Provides methods to send HTTP requests to the Movie API and returns its results.
 */
public class MovieApiService {

    private static MovieApiService instance;

    public static MovieApiService getInstance() {
        if (instance == null) {
            instance = new MovieApiService();
        }
        return instance;
    }

    private MovieApiService() {
    }

    private static PropertiesLoader propertiesLoader = new PropertiesLoader("api.properties");

    private static final String API_URL = propertiesLoader.getProperty("api.url");
    private static final String API_KEY = propertiesLoader.getProperty("api.key");


    /**
     * Sends an HTTP request to the Movie API with the specified endpoint.
     *
     * @param endpoint The API endpoint to send the request to.
     * @return The response body as a String.
     * @throws IOException If an I/O error occurs during the request.
     * @throws InterruptedException If the request is interrupted.
     */
    private static HttpResponse<String> sendHttpRequest(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Retrieves the authentication information from the Movie API.
     *
     * @return The response body as a String containing authentication details.
     * @throws IOException If an I/O error occurs during the request.
     * @throws InterruptedException If the request is interrupted.
     */
    public static String getAuthentication() throws IOException, InterruptedException {
        HttpResponse<String> response = sendHttpRequest("/authentication");
        return response.body();
    }

    /**
     * Searches for movies by their title
     *
     * @param query The search query, typically the movie title.
     * @return The response body as a String containing the search results.
     * @throws IOException If an I/O error occurs during the request.
     * @throws InterruptedException If the request is interrupted.
     */
    public static String searchMovies(String query) throws IOException, InterruptedException {
        String encodedQUery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        HttpResponse<String> response = sendHttpRequest("/search/movie?query=" + encodedQUery + "&include_adult=false&language=en-US");
        return response.body();
    }

    /**
     * Retrieves detailed information about a movie by its TMDB movie ID.
     *
     * @param tmdbMovieId The unique identifier for the movie in The Movie Database (TMDB).
     * @return The response body as a String containing the movie details.
     * @throws IOException If an I/O error occurs during the request.
     * @throws InterruptedException If the request is interrupted.
     */
    public static String getMovieDetails(Long tmdbMovieId) throws IOException, InterruptedException {
        HttpResponse<String> response = sendHttpRequest("/movie/" + tmdbMovieId + "?language=en-US");
        return response.body();
    }

}
