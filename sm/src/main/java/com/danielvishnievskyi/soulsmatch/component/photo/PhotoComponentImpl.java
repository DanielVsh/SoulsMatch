package com.danielvishnievskyi.soulsmatch.component.photo;

import com.danielvishnievskyi.soulsmatch.model.entity.Photo;
import com.danielvishnievskyi.soulsmatch.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotoComponentImpl implements PhotoComponent{

  private final PhotoRepository photoRepository;

  @Override
  public Photo createIfNotFound(String url) {
    return photoRepository.findByUrl(url)
      .orElseGet(() -> photoRepository.save(new Photo(null, url)));
  }
}
