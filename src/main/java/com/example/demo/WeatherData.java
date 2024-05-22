package com.example.demo;

import lombok.Data;

@Data
public class WeatherData {
    private Main main;
    private Weather[] weather;
    

    @Data
    public static class Main {
        private double temp; // 気温（ケルビン単位）
        private double humidity; // 湿度（パーセンテージ）
    }

    @Data
    public static class Weather {
        private String main; // 天気の説明
    }
}
