package com.pardis.server.services;

import com.pardis.server.domain.User;

public interface UserService extends CRUDService<User> {

    User findByUsername(String username);

}
