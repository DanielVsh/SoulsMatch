package com.danielvishnievskyi.soulsmatch.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "photo")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "url", nullable = false, unique = true)
  private String url;
}
