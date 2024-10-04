package com.bms.bms.model;

import java.util.List;

public class Screen {
    private Long id;
    private String name;
    private Long theaterId;
    private List<Seat> seats; // List of seats associated with the screen

    private Screen(ScreenBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.theaterId = builder.theaterId;
        this.seats = builder.seats; // Initialize the seats from the builder
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getTheaterId() {
        return theaterId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTheaterId(Long theaterId) {
        this.theaterId = theaterId;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public static class ScreenBuilder {
        private Long id;
        private String name;
        private Long theaterId;
        private List<Seat> seats; // Add seats to the builder

        public ScreenBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ScreenBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ScreenBuilder setTheaterId(Long theaterId) {
            this.theaterId = theaterId;
            return this;
        }

        public ScreenBuilder setSeats(List<Seat> seats) { // Method to set seats
            this.seats = seats;
            return this;
        }

        // Build method to create the Screen object
        public Screen build() {
            return new Screen(this);
        }
    }

    // Static method to initiate building
    public static ScreenBuilder builder() {
        return new ScreenBuilder();
    }

    @Override
    public String toString() {
        return "Screen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", theaterId=" + theaterId +
                ", seats=" + seats + // Include seats in toString
                '}';
    }
}
