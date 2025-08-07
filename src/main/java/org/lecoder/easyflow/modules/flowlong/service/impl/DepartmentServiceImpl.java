package org.lecoder.easyflow.modules.flowlong.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.modules.flowlong.dto.ApprovalTemplateDto;
import org.lecoder.easyflow.modules.flowlong.dto.FiledDto;
import org.lecoder.easyflow.modules.flowlong.entity.ApprovalTemplate;
import org.lecoder.easyflow.modules.flowlong.entity.Department;
import org.lecoder.easyflow.modules.flowlong.mapper.ApprovalTemplateMapper;
import org.lecoder.easyflow.modules.flowlong.mapper.DepartmentMapper;
import org.lecoder.easyflow.modules.flowlong.service.IApprovalTemplateService;
import org.lecoder.easyflow.modules.flowlong.service.IDepartmentService;
import org.lecoder.easyflow.modules.flowlong.service.IWeComUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lijile
 * @since 2021-10-26
 */
@Service
@AllArgsConstructor
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    private final IWeComUserService weComUserService;

    @Override
    public List<Department> selectList(Department department) {
        return this.getBaseMapper().selectList(department);
    }

    @Override
    public AjaxResult syncDepartment() {
        List<Department> deptList = weComUserService.getDeptList();
        if (!CollectionUtils.isEmpty(deptList)) {
            this.saveOrUpdateBatch(deptList);
        }
        return AjaxResult.success();
    }
}
