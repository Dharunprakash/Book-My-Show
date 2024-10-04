package com.bms.bms.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private long id;
    private String name;
    private String language;
    private String genre;
    private String duration;
    private String releaseDate;
    private float rating;
    private List<Showtime> showtimes;



    private Movie(MovieBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.language = builder.language;
        this.genre = builder.genre;
        this.duration = builder.duration;
        this.releaseDate = builder.releaseDate;
        this.rating = builder.rating;
        this.showtimes = builder.showtimes;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public float getRating() {
        return rating;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    public static class MovieBuilder {
        private long id;
        private String name;
        private String language;
        private String genre;
        private String duration;
        private String releaseDate;
        private float rating;
        private List<Showtime> showtimes = new ArrayList<>(); // Initialize with an empty list

        public MovieBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public MovieBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public MovieBuilder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public MovieBuilder setGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public MovieBuilder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public MovieBuilder setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public MovieBuilder setRating(float rating) {
            this.rating = rating;
            return this;
        }

        public MovieBuilder addShowtime(Showtime showtime) {
            this.showtimes.add(showtime); // Add a showtime to the list
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", genre='" + genre + '\'' +
                ", duration='" + duration + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating=" + rating +
                ", showtimes=" + showtimes +
                '}';
    }
}

