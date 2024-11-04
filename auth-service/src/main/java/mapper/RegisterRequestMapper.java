package mapper;

import model.dto.RegisterRequest;
import model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterRequestMapper {

    User toEntity(RegisterRequest registerRequest);
}
