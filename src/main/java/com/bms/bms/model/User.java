package com.bms.bms.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private long id;
    private String username;
    private String password;
    private String phone;
    private List<Booking> bookings; // List of bookings associated with the user

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.phone = builder.phone;
        this.bookings = builder.bookings; // Initialize bookings
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public static class UserBuilder {

        private long id;
        private String username;
        private String password;
        private String phone;
        private List<Booking> bookings = new ArrayList<>(); // Initialize with an empty list

        public UserBuilder(String username, String password) {
            this.username = username;
            this.password = password; // Consider hashing before storing
        }

        public UserBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder addBooking(Booking booking) {
            this.bookings.add(booking); // Add a booking to the list
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", bookings=" + bookings + // Include bookings in toString
                '}';
    }
}

