package org.lecoder.easyflow.modules.sys.dto;

import lombok.Data;

/**
 * 登录后返回用户实体
 *
 * @author: lijile
 * @date: 2021/10/27 10:24
 * @version: 1.0
 */
@Data
public class LoginUserDTO {
    private Long  id;
    /**
     * 用户名
     */
    private String username;

    private String password;
    /**
     * 姓名
     */
    private String fullname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 职务
     */
    private String position;

    /**
     * 部门
     */
    private String dept;

    private String token;
}
