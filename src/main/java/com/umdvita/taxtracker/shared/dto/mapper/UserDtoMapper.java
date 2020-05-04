package com.umdvita.taxtracker.shared.dto.mapper;

import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.enums.RoleType;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.dto.UserUpdateDto;
import com.umdvita.taxtracker.web.model.request.UserRequestModel;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The UserDtoMapper for the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {

  UserDtoMapper MAPPER = Mappers.getMapper(UserDtoMapper.class);

  @Named("userToUserDto")
  @Mapping(target = "role", qualifiedByName = "setRole")
  UserDto toUserDto(User user);

  UserDto toUserDto(UserRequestModel userRequestModel);

  @IterableMapping(qualifiedByName = "userToUserDto")
  List<UserDto> toUserDto(List<User> users);

  @Mapping(target = "role", qualifiedByName = "setRole")
  UserUpdateDto toUserUpdateDto(User user);

  @Mapping(target = "role", qualifiedByName = "setRole")
  UserUpdateDto toUserUpdateDto(UserDto userDto);

  User toUser(UserDto userDto);

  User toUser(UserUpdateDto userUpdateDto);

  @Named("setRole")
  default String setRole(User user) {
    if (user.getRoles().contains(new Role(RoleType.ADMIN))) {
      return RoleType.ADMIN.getRole();
    } else if (user.getRoles().contains(new Role(RoleType.LEADER))) {
      return RoleType.LEADER.getRole();
    } else if (user.getRoles().contains(new Role(RoleType.VOLUNTEER))) {
      return RoleType.VOLUNTEER.getRole();
    } else {
      return RoleType.USER.getRole();
    }
  }

}
