package org.lecoder.easyflow.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lecoder.easyflow.modules.sys.entity.SysUser;
import org.lecoder.easyflow.modules.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 流程接口测试
 *
 * @author: lijile
 * @date: 2021/10/25 16:31
 * @version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserServiceTest {

    @Test
    public void test() {
        List<SysUser> sysUsers = sysUserService.getBaseMapper().selectList(null);
        sysUsers.forEach(user -> {
            System.out.println(user.getUsername());
        });
    }


    @Autowired
    private ISysUserService sysUserService;

}
