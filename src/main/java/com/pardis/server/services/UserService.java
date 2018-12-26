package com.pardis.server.services;

import com.pardis.server.model.User;

public interface UserService extends CRUDService<User> {

    User findByUsername(String username);

}
