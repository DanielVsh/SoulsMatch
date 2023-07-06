package com.danielvishnievskyi.soulsmatch.service;

public interface CrudService<ResponseEntityDto, RequestEntityDto> {

  ResponseEntityDto getEntityById(Long id);

  ResponseEntityDto createEntity(RequestEntityDto requestEntityDTO);

  ResponseEntityDto updateEntity(Long id, RequestEntityDto requestEntityDTO);

  void deleteEntityById(Long id);
}
