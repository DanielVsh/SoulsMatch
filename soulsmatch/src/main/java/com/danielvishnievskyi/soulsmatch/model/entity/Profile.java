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
  @JoinColumn(name = "soul_id", nullable = false, unique = true)
  private Soul soul;

  @Column(name = "description")
  private String description;

  @OneToMany(fetch = EAGER)
  @CollectionTable(name = "photo_profile", joinColumns = @JoinColumn(name = "profile_id"))
  @ToString.Exclude
  private List<Photo> photos;

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "location_id")
  private Location location;

  @ElementCollection(fetch = EAGER)
  @CollectionTable(
    name = "profile_preferred_genders",
    joinColumns = @JoinColumn(name = "profile_id", nullable = false)
  )
  @Enumerated(EnumType.STRING)
  private List<Gender> preferredGenders;


}
