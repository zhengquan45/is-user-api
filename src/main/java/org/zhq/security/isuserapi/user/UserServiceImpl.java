package org.zhq.security.isuserapi.user;

import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public UserInfo update(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        userRepository.save(user);
        return userInfo;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserInfo get(Long id) {
        return userRepository.findById(id).get().buildInfo();
    }

    @Override
    public List<UserInfo> query(String name) {
        List<User> userList = userRepository.findAllByUsername(name);
        List<UserInfo> userInfoList = userList.stream().map(User::buildInfo).collect(Collectors.toList());
        return userInfoList;
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        User user = userRepository.findUserByUsernameAndAndPassword(userInfo.getUsername(), userInfo.getPassword());
        return user.buildInfo();
    }
}
