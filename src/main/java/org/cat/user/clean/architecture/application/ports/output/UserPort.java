package org.cat.user.clean.architecture.application.ports.output;

import org.cat.user.clean.architecture.domain.model.User;

public interface UserPort {

    User create(User user);

}
