package org.lecoder.easyflow.modules.flowlong.service;


import org.lecoder.easyflow.modules.flowlong.entity.Department;
import org.lecoder.easyflow.modules.flowlong.entity.WxUser;

import java.util.List;

/**
 * @author Administrator
 */
public interface IWeComUserService {
    /**
     * 获取企微员工数据
     *
     * @return 部门数据
     */
    List<WxUser> getUserlist();


    /**
     * 获取企微部门数据
     *
     * @return 部门数据
     */
    List<Department> getDeptList();
}
