package com.danielvishnievskyi.soulsmatch.model.dto.response;

import com.danielvishnievskyi.soulsmatch.model.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoulResponseDto {
  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private @Enumerated(EnumType.STRING) Gender gender;
  private int age;
  private LocationResponseDto location;
  private List<PhotoResponseDto> photos;
}
