package com.danielvishnievskyi.soulsmatch.model.entity;


import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import com.danielvishnievskyi.soulsmatch.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "soul")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Soul implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "username", unique = true, nullable = false)
  private String email;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "firstName", nullable = false)
  private String firstName;

  @Column(name = "lastName", nullable = false)
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", nullable = false)
  private Gender gender;

  @Column(name = "birthDate", nullable = false)
  private LocalDate birthDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "location")
  private Location location;

  @OneToMany(fetch = FetchType.EAGER)
  @CollectionTable(name = "photo_soul", joinColumns = @JoinColumn(name = "soul_id"))
  @ToString.Exclude
  private List<Photo> photos;

  @Enumerated(EnumType.STRING)
  @Column(name = "roles", nullable = false)
  private List<Role> roles;

  public int getAge() {
    LocalDate currentDate = LocalDate.now();
    Period period = Period.between(birthDate, currentDate);
    return period.getYears();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).toList();
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
