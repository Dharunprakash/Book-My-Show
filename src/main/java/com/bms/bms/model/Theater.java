package com.bms.bms.model;

import java.util.List;

public class Theater {

    private long id;
    private String name;
    private String location;
    private String address;
    private List<Screen> screens;

    private Theater(TheaterBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.location = builder.location;
        this.address = builder.address;
        this.screens = builder.screens;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setScreens(List<Screen> screens) {
        this.screens = screens;
    }

    public static class TheaterBuilder {
        private long id;
        private String name;
        private String location;
        private String address;
        private List<Screen> screens; // Added screens to the builder

        public TheaterBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public TheaterBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public TheaterBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public TheaterBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public TheaterBuilder setScreens(List<Screen> screens) { // Method to set screens
            this.screens = screens;
            return this;
        }

        public Theater build() {
            return new Theater(this);
        }

        @Override
        public String toString() {
            return "TheaterBuilder{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", location='" + location + '\'' +
                    ", address='" + address + '\'' +
                    ", screens=" + screens +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Theater{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", screens=" + screens +
                '}';
    }
}
