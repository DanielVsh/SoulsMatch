package com.danielvishnievskyi.soulsmatch.service.user;

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
    //TODO: throw the exception
    return soulMapperService.entityToResponseDto(soulRepository.findByEmail(username).orElseThrow());
  }

  @Override
  public SoulResponseDto getEntityById(Long id) {
    Soul soul = soulRepository.findById(id).orElseThrow();
    return soulMapperService.entityToResponseDto(soul);
  }

  @Override
  public SoulResponseDto createEntity(SoulRequestDto requestEntityDTO) {
    return soulMapperService.entityToResponseDto(
      soulRepository.save(soulMapperService.requestDtoToEntity(requestEntityDTO))
    );
  }

  @Override
  public SoulResponseDto updateEntity(Long id, SoulRequestDto requestEntityDTO) {
    //TODO: throw the exception
    Soul user = soulRepository.findById(id).orElseThrow();
    soulMapperService.updateEntityByRequestDto(requestEntityDTO, user);
    return soulMapperService.entityToResponseDto(soulRepository.save(user));
  }

  @Override
  public void deleteEntityById(Long id) {
    soulRepository.deleteById(id);
  }
}
