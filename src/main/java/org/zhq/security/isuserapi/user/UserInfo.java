package org.zhq.security.isuserapi.user;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
public class UserInfo {
    private Long id;
    private String name;
    @NotBlank(message = "用户名不能为空")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String permissions;
}
