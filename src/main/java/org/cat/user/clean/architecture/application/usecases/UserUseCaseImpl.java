package org.cat.user.clean.architecture.application.usecases;

import org.cat.user.clean.architecture.application.ports.input.IUserUseCase;
import org.cat.user.clean.architecture.application.ports.output.UserPort;
import org.cat.user.clean.architecture.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserUseCaseImpl implements IUserUseCase {

    private final UserPort userPort;

    public UserUseCaseImpl(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public User create(User user) {
        return userPort.create(user);
    }
}
