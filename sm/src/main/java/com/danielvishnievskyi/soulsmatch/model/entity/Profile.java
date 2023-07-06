package com.danielvishnievskyi.soulsmatch.model.entity;


import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "profile")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @OneToOne
  @JoinColumn(name = "soul", nullable = false, unique = true)
  private Soul soul;

  @Column(name = "description")
  private String description;

  @OneToMany(fetch = FetchType.EAGER)
  @CollectionTable(name = "photo_profile", joinColumns = @JoinColumn(name = "profile_id"))
  @ToString.Exclude
  private List<Photo> photos;

  @ElementCollection(fetch = EAGER)
  @CollectionTable(name = "profile_preferred_genders", joinColumns = @JoinColumn(name = "profile_id"))
  @Enumerated(EnumType.STRING)
  private List<Gender> preferredGenders;
}
