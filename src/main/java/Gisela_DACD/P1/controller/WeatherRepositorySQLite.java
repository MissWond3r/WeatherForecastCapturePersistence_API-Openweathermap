package Gisela_DACD.P1.controller;

import Gisela_DACD.P1.model.Location;
import Gisela_DACD.P1.model.Weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class WeatherRepositorySQLite implements WeatherRepository {

    public final Connection connection;
    public WeatherRepositorySQLite(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveWeatherData(Location location, Weather weather) throws SQLException {

        String islandName = location.getName();
        String insertSQL = "INSERT INTO table_" + islandName + "_weather (datetime, temperature, precipitation, " +
                "humidity, clouds, wind_speed) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)){
            preparedStatement.setString(1, weather.getTs().toString());
            preparedStatement.setDouble(2, weather.getTemperature());
            preparedStatement.setDouble(3, weather.getPrecipitation());
            preparedStatement.setDouble(4, weather.getHumidity());
            preparedStatement.setDouble(5, weather.getClouds());
            preparedStatement.setDouble(6, weather.getWindSpeed());
            preparedStatement.executeUpdate();

            System.out.println("Weather data inserted into the corresponding table.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}