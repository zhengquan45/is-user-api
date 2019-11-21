package org.zhq.security.isuserapi.user;

import antlr.StringUtils;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String permissions;

    public UserInfo buildInfo() {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(this,userInfo);
        return userInfo;
    }

    public boolean hasPermission(String method) {
        boolean result;
        if(StrUtil.equalsIgnoreCase("get",method)){
            result = StrUtil.containsIgnoreCase(permissions,"r");
        }else{
            result = StrUtil.containsIgnoreCase(permissions,"w");
        }
        return result;
    }
}
