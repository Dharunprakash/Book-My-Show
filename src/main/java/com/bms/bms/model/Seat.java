package com.bms.bms.model;


public class Seat {
    private Long id;
    private Long screenId;
    private int seatNumber;

    private Seat(SeatBuilder builder) {
        this.id = builder.id;
        this.screenId = builder.screenId;
        this.seatNumber = builder.seatNumber;
    }


    public Long getId() {
        return id;
    }

    public Long getScreenId() {
        return screenId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }


    public static class SeatBuilder {
        private Long id;
        private Long screenId;
        private int seatNumber;


        public SeatBuilder setId(Long id) {
            this.id = id;
            return this;
        }


        public SeatBuilder setScreenId(Long screenId) {
            this.screenId = screenId;
            return this;
        }


        public SeatBuilder setSeatNumber(int seatNumber) {
            this.seatNumber = seatNumber;
            return this;
        }


        public Seat build() {
            return new Seat(this);
        }
    }


    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", screenId=" + screenId +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
