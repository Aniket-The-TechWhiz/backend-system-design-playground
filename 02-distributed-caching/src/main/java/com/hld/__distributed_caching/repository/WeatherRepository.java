package com.hld.__distributed_caching.repository;

import com.hld.__distributed_caching.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather,Long> {
    Optional<Weather> findByCity(String city);
    void deleteByCity(String city);
}
