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
public class ProfileResponseDto {
  private Long id;
  private SoulResponseDto soul;
  private String description;
  private LocationResponseDto location;
  @Enumerated(EnumType.STRING)
  private List<Gender> preferredGenders;
  private List<PhotoResponseDto> photos;
}
