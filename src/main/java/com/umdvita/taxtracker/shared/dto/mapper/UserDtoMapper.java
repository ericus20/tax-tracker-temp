package com.umdvita.taxtracker.shared.dto.mapper;

import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.shared.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {

  UserDtoMapper MAPPER = Mappers.getMapper( UserDtoMapper.class );

  UserDto toUserDto(User user);
  User toUser(UserDto userDto);

}
