package com.hld.__distributed_caching.controller;

import com.hld.__distributed_caching.model.Weather;
import com.hld.__distributed_caching.repository.WeatherRepository;
import com.hld.__distributed_caching.service.CacheInspectionService;
import com.hld.__distributed_caching.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private CacheInspectionService cacheInspectionService;

    @GetMapping("cacheData")
    public void getCacheData(){
        cacheInspectionService.printCacheContents("weather");
    }

    @GetMapping
    public String getWeather(@RequestParam String city){

            return weatherService.getWeather(city);
    }

    @PostMapping
    public Weather addWeather(@RequestBody Weather weather){
        return weatherRepository.save(weather);
    }

    @GetMapping("/all")
    public List<Weather> getAllWeather(){
        return weatherRepository.findAll();
    }

    @PutMapping("{city}")
    public String updateWeather(@PathVariable String city ,@RequestParam String weatherUpdate){
        String u=weatherService.updateWeather(city,weatherUpdate);
        if(u==null){
            return "Cannot update city is not in DB";
        }
        return u;
    }

    @DeleteMapping("{city}")
    public String deleteWeather(@PathVariable String city){
        weatherService.deleteWeather(city);
        return "Weather data for "+city+" has been deleted and cache evicted.";
    }
}
