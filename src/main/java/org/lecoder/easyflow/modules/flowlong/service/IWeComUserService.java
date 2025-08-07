package org.lecoder.easyflow.modules.flowlong.service;


import org.lecoder.easyflow.modules.flowlong.dto.wxDto.WeComUserInfoDto;
import org.lecoder.easyflow.modules.flowlong.entity.Department;
import org.lecoder.easyflow.modules.flowlong.entity.WxUser;

import java.util.List;

/**
 * @author Administrator
 */
public interface IWeComUserService {

     List<WxUser> getUserlist();


    WeComUserInfoDto getUserIdInfo();


    /**
     * 获取企微部门数据
     * @return 部门数据
     */
    List<Department> getDeptList();
}
