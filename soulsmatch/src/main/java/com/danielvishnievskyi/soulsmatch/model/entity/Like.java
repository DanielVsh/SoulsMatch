package com.danielvishnievskyi.soulsmatch.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "likes")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Like {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "soul_id")
  private Soul soul;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "likes_liked_profiles",
    joinColumns = @JoinColumn(name = "like_id"),
    inverseJoinColumns = @JoinColumn(name = "profile_id"))
  @ToString.Exclude
  private Set<Profile> likedProfiles;
}
