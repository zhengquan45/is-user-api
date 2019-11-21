package org.zhq.security.isuserapi.user;

import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo create(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        user.setPassword(SCryptUtil.scrypt(user.getPassword(),32768,8,1));
        userRepository.save(user);
        userInfo.setId(user.getId());
        return userInfo;
    }

    @Override
    public UserInfo update(UserInfo user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserInfo get(Long id) {
        return userRepository.findById(id).get().buildInfo();
    }

    @Override
    public List<UserInfo> query(String name) {
        return null;
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        return null;
    }
}
