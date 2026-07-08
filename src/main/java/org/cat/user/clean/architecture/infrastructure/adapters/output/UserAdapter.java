package org.cat.user.clean.architecture.infrastructure.adapters.output;

import org.cat.user.clean.architecture.application.ports.output.UserPort;
import org.cat.user.clean.architecture.domain.model.User;
import org.cat.user.clean.architecture.infrastructure.adapters.output.mapper.UserEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserPort {

    private final UserEntityRepository repository;

    public UserAdapter(UserEntityRepository repository){
        this.repository = repository;
    }

    @Override
    public User create(User user) {
         UserEntity userEntity = repository.save(
                UserEntityMapper.INSTANCE.toUserEntity(user)
        );
        return UserEntityMapper.INSTANCE.toUser(userEntity);
    }

}
