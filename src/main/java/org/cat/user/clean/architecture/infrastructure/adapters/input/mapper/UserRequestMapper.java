package org.cat.user.clean.architecture.infrastructure.adapters.input.mapper;

import org.cat.user.clean.architecture.domain.model.User;
import org.cat.user.clean.architecture.infrastructure.adapters.input.dto.UserRequest;
import org.cat.user.clean.architecture.infrastructure.adapters.input.dto.UserResponse;
import org.cat.user.clean.architecture.infrastructure.adapters.output.mapper.UserEntityMapper;
import org.mapstruct.factory.Mappers;

public interface UserRequestMapper {
    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);

    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);
}
