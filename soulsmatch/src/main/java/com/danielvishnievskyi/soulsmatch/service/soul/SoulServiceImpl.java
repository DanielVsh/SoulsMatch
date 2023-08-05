package com.danielvishnievskyi.soulsmatch.service.soul;

import com.danielvishnievskyi.soulsmatch.exception.soul.SoulNotFoundException;
import com.danielvishnievskyi.soulsmatch.mapper.soul.SoulMapperService;
import com.danielvishnievskyi.soulsmatch.model.dto.request.SoulRequestDto;
import com.danielvishnievskyi.soulsmatch.model.dto.response.SoulResponseDto;
import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import com.danielvishnievskyi.soulsmatch.repository.SoulRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoulServiceImpl implements SoulService {

  private final SoulRepository soulRepository;
  private final SoulMapperService soulMapperService;


  @Override
  public SoulResponseDto findByUsername(String username) {
    return soulMapperService.entityToResponseDto(getSoulByUsernameOrElseThrow(username));
  }


  @Override
  public SoulResponseDto getEntityById(Long id) {
    return soulMapperService.entityToResponseDto(getSoulByIdOrElseThrow(id));
  }

  @Override
  public void createEntity(SoulRequestDto requestEntityDTO) {
    soulRepository.save(soulMapperService.requestDtoToEntity(requestEntityDTO));
  }

  @Override
  public void updateEntity(Long id, SoulRequestDto requestEntityDTO) {
    Soul user = getSoulByIdOrElseThrow(id);
    soulMapperService.updateEntityByRequestDto(requestEntityDTO, user);
    soulRepository.save(user);
  }

  @Override
  public void deleteEntityById(Long id) {
    soulRepository.deleteById(id);
  }

  private Soul getSoulByUsernameOrElseThrow(String username) {
    return soulRepository.findByUsername(username)
      .orElseThrow(() -> new SoulNotFoundException("Soul with username: %s not found".formatted(username)));
  }

  private Soul getSoulByIdOrElseThrow(Long id) {
    return soulRepository.findById(id)
      .orElseThrow(() -> new SoulNotFoundException("Soul with id: %d not found".formatted(id)));
  }
}
