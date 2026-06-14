package com.hld.__distributed_caching.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="weather")
public class Weather {
    @Id
    @GeneratedValue
    private Long id;

    private String city;

    private String forecast;

    public Weather() {
    }

    public Weather(String city, String forecast) {
        this.city = city;
        this.forecast = forecast;
    }
}
