package org.zhq.security.isuserapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public void login(@Validated UserInfo userInfo,HttpServletRequest request) {
        UserInfo info = userService.login(userInfo);
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        request.getSession(true).setAttribute("user",info);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }


    @PostMapping
    public UserInfo create(@RequestBody @Validated UserInfo user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserInfo update(@RequestBody UserInfo user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserInfo get(@PathVariable Long id, HttpServletRequest request) {
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        if(user ==null ||!user.getId().equals(id)){
            throw new RuntimeException("Authentication exception. get userInfo fail");
        }

        return user;
    }

    @GetMapping
    public List<UserInfo> query(String name) {
        return userService.query(name);
    }

}
