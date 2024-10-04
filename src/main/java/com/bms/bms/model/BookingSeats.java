package com.bms.bms.model;

public class BookingSeats {
    private Long id;
    private Long bookingId;
    private Long seatId;

    private BookingSeats(BookingSeatsBuilder builder) {
        this.id = builder.id;
        this.bookingId = builder.bookingId;
        this.seatId = builder.seatId;
    }

    public Long getId() {
        return id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public static class BookingSeatsBuilder {
        private Long id;
        private Long bookingId;
        private Long seatId;

        public BookingSeatsBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public BookingSeatsBuilder setBookingId(Long bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public BookingSeatsBuilder setSeatId(Long seatId) {
            this.seatId = seatId;
            return this;
        }


        public BookingSeats build() {
            return new BookingSeats(this);
        }

        @Override
        public String toString() {
            return "BookingSeatsBuilder{" +
                    "id=" + id +
                    ", bookingId=" + bookingId +
                    ", seatId=" + seatId +
                    '}';
        }
    }

    public static BookingSeatsBuilder builder() {
        return new BookingSeatsBuilder();
    }

    @Override
    public String toString() {
        return "BookingSeats{" +
                "id=" + id +
                ", bookingId=" + bookingId +
                ", seatId=" + seatId +
                '}';
    }
}
