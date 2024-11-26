package ch.nb.api.business;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.Locale;

public class Movie {

    private Long id;

    private Long tmdbMovieId;

    private String title;

    private Float voteAverage;

    private LocalDate releaseDate;

    private Integer durationInSeconds;

    private String OriginalTitle;

    private String OriginalLanguage;

    private Map<Integer, String> genresMap;

    private String imagePath;

    private BigDecimal budget;

    public Movie(Long tmdbMovieId, String title) {
        this.tmdbMovieId = tmdbMovieId;
        this.title = title;
    }

    public Movie(Long tmdbMovieId, String title, Float voteAverage, LocalDate releaseDate, Integer durationInSeconds, String OriginalTitle, String OriginalLanguage, String imagePath) {
        this.tmdbMovieId = tmdbMovieId;
        this.title = title;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.durationInSeconds = durationInSeconds;
        this.OriginalTitle = OriginalTitle;
        this.OriginalLanguage = OriginalLanguage;
        this.imagePath = imagePath;
    }

    public Movie(Long tmdbMovieId, String title, Float voteAverage, LocalDate releaseDate, Integer durationInSeconds , String OriginalTitle, String OriginalLanguage, Map<Integer, String> genresMap, String imagePath) {
        this.tmdbMovieId = tmdbMovieId;
        this.title = title;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.durationInSeconds = durationInSeconds;
        this.OriginalTitle = OriginalTitle;
        this.OriginalLanguage = OriginalLanguage;
        this.genresMap = genresMap;
        this.imagePath = imagePath;
    }

    public Movie(Long tmdbMovieId, String title, Float voteAverage, LocalDate releaseDate, Integer durationInSeconds, String OriginalTitle, String OriginalLanguage, Map<Integer, String> genresMap, String imagePath, BigDecimal budget) {
        this.tmdbMovieId = tmdbMovieId;
        this.title = title;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.durationInSeconds = durationInSeconds;
        this.OriginalTitle = OriginalTitle;
        this.OriginalLanguage = OriginalLanguage;
        this.genresMap = genresMap;
        this.imagePath = imagePath;
        this.budget = budget;
    }

    public Long getId() {
        return id;
    }

    public Long getTmdbMovieId() {
        return tmdbMovieId;
    }

    public String getTitle() {
        return title;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public String getOriginalLanguage() {
        return OriginalLanguage;
    }

    public Map<Integer, String> getGenresMap() {
        return genresMap;
    }

    public String getImagePath() {
        return imagePath;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTmdbMovieId(Long tmdbMovieId) {
        this.tmdbMovieId = tmdbMovieId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setOriginalTitle(String OriginalTitle) {
        this.OriginalTitle = OriginalTitle;
    }

    public void setOriginalLanguage(String OriginalLanguage) {
        this.OriginalLanguage = OriginalLanguage;
    }

    public void setGenreIds(Map<Integer, String> genresMap) {
        this.genresMap = genresMap;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public static String formatCurrency(BigDecimal value) {
        // Create a NumberFormat instance with grouping separator (space)
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRENCH);
        numberFormat.setGroupingUsed(true); // Use grouping (thousands separator)

        // Format the BigDecimal value
        String formattedValue = numberFormat.format(value);

        return formattedValue + " $";
    }

    public static class Builder {
        private Long id;
        private Long tmdbMovieId;
        private String title;
        private Float voteAverage;
        private LocalDate releaseDate;
        private Integer durationInSeconds;
        private String OriginalTitle;
        private String OriginalLanguage;
        private Map<Integer, String> genresMap;
        private String imagePath;
        private BigDecimal budget;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withTmdbMovieId(Long tmdbMovieId) {
            this.tmdbMovieId = tmdbMovieId;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withVoteAverage(Float voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }

        public Builder withReleaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder withDurationInSeconds(Integer durationInSeconds) {
            this.durationInSeconds = durationInSeconds;
            return this;
        }

        public Builder withOriginalTitle(String OriginalTitle) {
            this.OriginalTitle = OriginalTitle;
            return this;
        }

        public Builder withOriginalLanguage(String OriginalLanguage) {
            this.OriginalLanguage = OriginalLanguage;
            return this;
        }

        public Builder withGenresMap(Map<Integer, String> genresMap) {
            this.genresMap = genresMap;
            return this;
        }

        public Builder withImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder withBudget(BigDecimal budget) {
            this.budget = budget;
            return this;
        }

        public Movie build() {
            return new Movie(tmdbMovieId, title, voteAverage, releaseDate, durationInSeconds, OriginalTitle, OriginalLanguage, genresMap, imagePath, budget);
        }
    }
}