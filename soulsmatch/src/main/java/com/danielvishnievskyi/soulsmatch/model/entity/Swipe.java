package com.danielvishnievskyi.soulsmatch.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "swipe")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Swipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "soul_id", nullable = false, unique = true)
  private Soul soul;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "swipe_profile",
    joinColumns = @JoinColumn(name = "swipe_id"),
    inverseJoinColumns = @JoinColumn(name = "profile_id"))
  @ToString.Exclude
  private Set<Profile> swipedProfiles;
}
