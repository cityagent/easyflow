package org.lecoder.easyflow.modules.flowlong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.modules.flowlong.dto.ApprovalTemplateDto;
import org.lecoder.easyflow.modules.flowlong.entity.ApprovalTemplate;
import org.lecoder.easyflow.modules.flowlong.entity.Department;

import java.util.List;

/**
 * <p>
 * 企微部门接口
 * </p>
 *
 * @author lijile
 * @since 2021-10-26
 */
public interface IDepartmentService extends IService<Department> {


    /**
     * 查询列表
     * @param department 参数
     * @return 列表
     */
    List<Department> selectList(Department department);


    /**
     * 企微微信部门 同步
     * @return AjaxResult
     */
    AjaxResult syncDepartment();



}
