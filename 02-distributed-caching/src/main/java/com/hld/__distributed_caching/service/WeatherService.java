package com.hld.__distributed_caching.service;

import com.hld.__distributed_caching.model.Weather;
import com.hld.__distributed_caching.repository.WeatherRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Cacheable(cacheNames = "weather",
            key="#city",
            unless = "#result == 'Weather data not available'") //is used to store the frequently requested data into cache
    public String getWeather(String city) {
        System.out.println("Fetching data from DB for city : "+ city);
        Optional<Weather> weather=weatherRepository.findByCity(city);
        return weather.map(Weather::getForecast).orElse("Weather data not available");
    }

    @CachePut(
            cacheNames = "weather",
            key = "#city",
            unless = "#result == null"
    )//is used to store the updated weather into cache
    public String updateWeather(String city, String updatedForecast) {

        Optional<Weather> optionalWeather = weatherRepository.findByCity(city);

        if (optionalWeather.isPresent()) {
            Weather weather = optionalWeather.get();
            weather.setForecast(updatedForecast);
            weatherRepository.save(weather);

            return weather.getForecast();
        }

        System.out.println("City not found: " + city);
        return null;
    }

    @Transactional
    @CacheEvict(cacheNames ="weather",key="#city")//once data is deleted from DB also get delete from cache
    public void deleteWeather(String city) {
        System.out.println("Removing weather data for city: "+ city);
        weatherRepository.deleteByCity(city);
    }
}
