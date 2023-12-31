package org.gisela.dacd.provider.application;

import org.gisela.dacd.provider.domain.Location;
import org.gisela.dacd.provider.domain.Weather;
import java.util.List;

public interface WeatherProvider {
    List<Weather> getWeatherData(Location location);
}
