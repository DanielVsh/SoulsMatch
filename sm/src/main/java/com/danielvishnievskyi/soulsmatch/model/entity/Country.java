package com.danielvishnievskyi.soulsmatch.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "country")
@Getter
@Setter
@ToString
@AllArgsConstructor()
@NoArgsConstructor
public class Country {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @OneToMany(fetch = EAGER)
  @JoinColumn(name = "country")
  @ToString.Exclude
  private List<City> cities;
}
