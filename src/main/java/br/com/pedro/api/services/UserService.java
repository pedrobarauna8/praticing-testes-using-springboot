package br.com.pedro.api.services;

import br.com.pedro.api.domain.User;
import br.com.pedro.api.domain.UserDTO;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO obj);
    User update(UserDTO obj);
    void delete(Integer id);
}
