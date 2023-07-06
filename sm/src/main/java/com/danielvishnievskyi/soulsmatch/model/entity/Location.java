package com.danielvishnievskyi.soulsmatch.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "location")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "country", nullable = false)
  private Country country;

  @ManyToOne
  @JoinColumn(name = "city", nullable = false)
  private City city;

  @Column(name = "latitude", nullable = false)
  private double latitude;

  @Column(name = "longitude", nullable = false)
  private double longitude;

  public Location(Long id, Country country, City city) {
    this.id = id;
    this.country = country;
    this.city = city;
    this.latitude = city.getLatitude();
    this.longitude = city.getLongitude();
  }
}
