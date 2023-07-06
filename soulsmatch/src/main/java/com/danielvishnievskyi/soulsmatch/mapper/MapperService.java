package com.danielvishnievskyi.soulsmatch.mapper;

import org.mapstruct.MappingTarget;

public interface MapperService<Entity, RequestDto, ResponseDto> {

  Entity requestDtoToEntity(RequestDto requestDto);

  ResponseDto entityToResponseDto(Entity entity);

  void updateEntityByRequestDto(RequestDto requestDto, @MappingTarget Entity entity);
}
