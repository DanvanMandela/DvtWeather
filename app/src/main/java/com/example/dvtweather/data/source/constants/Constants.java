package com.example.dvtweather.data.source.constants;


import org.jetbrains.annotations.NotNull;

public class Constants {
    public static class Timeout {
        public static long connection = 60 * 10000;
        public static long read = 60 * 10000;
        public static long write = 60 * 10000;
    }

    public static class BaseUrl {

        @NotNull
        public static final String BASE_URL = "https://api.openweathermap.org/";

        @NotNull
        public static final String MAP_URL = "https://maps.googleapis.com/";

    }

    public static class MapsConstants {
        public static long interval = 600 * 10000;
        public static long fastestInterval = 5 * 10000;
        public static long maxWaitTime = 5 * 10000;
    }

    public static class RoomDatabase {
        public static final String DATABASE_NAME = "local-db";
    }
}
