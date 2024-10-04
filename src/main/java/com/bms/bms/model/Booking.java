package com.bms.bms.model;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    private Long id;
    private Long userId;
    private Long showtimeId;
    private List<BookingSeats> bookedSeats;


    private Booking(BookingBuilder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.showtimeId = builder.showtimeId;
        this.bookedSeats = builder.bookedSeats;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public List<BookingSeats> getBookedSeats() {
        return bookedSeats;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    public void setBookedSeats(List<BookingSeats> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public static class BookingBuilder {
        private Long id;
        private Long userId;
        private Long showtimeId;
        private List<BookingSeats> bookedSeats = new ArrayList<>();

        public BookingBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public BookingBuilder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BookingBuilder setShowtimeId(Long showtimeId) {
            this.showtimeId = showtimeId;
            return this;
        }
        public BookingBuilder addSeat(BookingSeats seat) {
            this.bookedSeats.add(seat);
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }

        @Override
        public String toString() {
            return "BookingBuilder{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", showtimeId=" + showtimeId +
                    ", bookedSeats=" + bookedSeats +
                    '}';
        }
    }


}
