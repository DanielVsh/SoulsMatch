package com.danielvishnievskyi.soulsmatch.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "photo")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  private String key;

  private String bucket;
}
