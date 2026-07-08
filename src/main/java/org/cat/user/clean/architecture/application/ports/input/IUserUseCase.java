package org.cat.user.clean.architecture.application.ports.input;

import org.cat.user.clean.architecture.domain.model.User;

public interface IUserUseCase {
    User create (User user);
}
