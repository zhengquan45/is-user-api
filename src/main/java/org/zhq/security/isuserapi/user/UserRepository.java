package org.zhq.security.isuserapi.user;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User,Long> {

    User findByUsername(String username);

    List<User> findAllByUsername(String name);

    User findUserByUsernameAndAndPassword(String username,String password);
}
