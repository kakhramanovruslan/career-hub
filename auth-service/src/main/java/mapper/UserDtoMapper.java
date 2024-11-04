package mapper;

import model.dto.UserDto;
import model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
