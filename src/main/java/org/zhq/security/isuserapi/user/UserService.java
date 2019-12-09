package org.zhq.security.isuserapi.user;

import java.util.List;

public interface UserService {

    default UserInfo create(UserInfo user){
        return user;
    }

    UserInfo update(UserInfo user);

    void delete(Long id);

    UserInfo get(Long id);

    List<UserInfo> query(String name);

    UserInfo login(UserInfo userInfo);
}
