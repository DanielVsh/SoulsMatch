package com.danielvishnievskyi.soulsmatch.util.location;

import com.danielvishnievskyi.soulsmatch.model.entity.Location;
import com.danielvishnievskyi.soulsmatch.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationServiceUtilImpl implements LocationServiceUtil {

  private final LocationRepository locationRepository;

  @Override
  public Location createIfNotFound(String name, double latitude, double longitude) {
    return locationRepository.findByName(name)
      .orElseGet(() -> locationRepository.save(new Location(null, name, latitude, longitude)));
  }
}
