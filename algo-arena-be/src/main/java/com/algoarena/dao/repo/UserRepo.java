package com.algoarena.dao.repo;

import com.algoarena.dao.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, String> {
}
