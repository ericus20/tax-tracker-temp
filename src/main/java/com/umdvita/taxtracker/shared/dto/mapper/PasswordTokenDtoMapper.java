package com.umdvita.taxtracker.shared.dto.mapper;

import com.umdvita.taxtracker.backend.persistence.domain.security.PasswordToken;
import com.umdvita.taxtracker.shared.dto.PasswordTokenDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * The UserDtoMapper for the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PasswordTokenDtoMapper {

  PasswordTokenDtoMapper MAPPER = Mappers.getMapper(PasswordTokenDtoMapper.class);

  PasswordTokenDto toPasswordTokenDto(PasswordToken passwordToken);

  PasswordToken toPasswordToken(PasswordTokenDto passwordTokenDto);

}
