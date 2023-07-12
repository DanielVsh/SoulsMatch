package com.danielvishnievskyi.soulsmatch.util.location;

import com.danielvishnievskyi.soulsmatch.model.entity.Location;

public interface LocationServiceUtil {
  Location createIfNotFound(String name, double latitude, double longitude);
}
