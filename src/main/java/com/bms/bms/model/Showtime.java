package com.bms.bms.model;
import java.util.Date;

public class Showtime {
    private Long id;
    private Long screenId;
    private Long movieId;
    private Date startTime;
    private Float price;
    private Integer availableSeats;


    public Showtime() {}

    public Showtime(Long id, Long screenId, Long movieId, Date startTime, Float price, Integer availableSeats) {
        this.id = id;
        this.screenId = screenId;
        this.movieId = movieId;
        this.startTime = startTime;
        this.price = price;
        this.availableSeats = availableSeats;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScreenId() {
        return screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }


    public static class ShowtimeBuilder {
        private Long id;
        private Long screenId;
        private Long movieId;
        private Date startTime;
        private Float price;
        private Integer availableSeats;


        public ShowtimeBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ShowtimeBuilder setScreenId(Long screenId) {
            this.screenId = screenId;
            return this;
        }

        public ShowtimeBuilder setMovieId(Long movieId) {
            this.movieId = movieId;
            return this;
        }

        public ShowtimeBuilder setStartTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public ShowtimeBuilder setPrice(Float price) {
            this.price = price;
            return this;
        }

        public ShowtimeBuilder setAvailableSeats(Integer availableSeats) {
            this.availableSeats = availableSeats;
            return this;
        }

        public Showtime build() {
            return new Showtime(id, screenId, movieId, startTime, price, availableSeats);
        }
    }


    @Override
    public String toString() {
        return "Showtime{" +
                "id=" + id +
                ", screenId=" + screenId +
                ", movieId=" + movieId +
                ", startTime=" + startTime +
                ", price=" + price +
                ", availableSeats=" + availableSeats +
                '}';
    }
}

