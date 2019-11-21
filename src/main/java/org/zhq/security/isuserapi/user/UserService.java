package org.zhq.security.isuserapi.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserService {

    UserInfo create(UserInfo user);

    UserInfo update(UserInfo user);

    void delete(Long id);

    UserInfo get(Long id);

    List<UserInfo> query(String name);

    UserInfo login(UserInfo userInfo);
}
