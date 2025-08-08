package org.lecoder.easyflow.modules.flowlong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.modules.flowlong.dto.DepartmentDto;
import org.lecoder.easyflow.modules.flowlong.entity.Department;
import org.lecoder.easyflow.modules.flowlong.mapper.DepartmentMapper;
import org.lecoder.easyflow.modules.flowlong.service.IDepartmentService;
import org.lecoder.easyflow.modules.flowlong.service.IWeComUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public List<DepartmentDto> selectList(DepartmentDto department) {
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

    @Override
    public List<DepartmentDto> getDepartmentTree(DepartmentDto department) {
        List<DepartmentDto> departments = selectList(department);
        return buildTree(departments);
    }

    public static List<DepartmentDto> buildTree(List<DepartmentDto> list) {

        // 最终返回的树形结构
        List<DepartmentDto> tree = new ArrayList<>();


        Map<Long, DepartmentDto> departmentChildrenMap = list.stream().collect(Collectors.toMap(DepartmentDto::getId, Function.identity()));

        // 第二次遍历：设置父子关系
        for (DepartmentDto departmentChildren : list) {
            Long parentId = departmentChildren.getParentId();
            if (parentId == 0 || !departmentChildrenMap.containsKey(parentId)) {
                // parentId为空或不存在，说明是根节点
                tree.add(departmentChildren);
            } else {
                // 找到父节点，并将当前节点添加到父节点的children中
                DepartmentDto parent = departmentChildrenMap.get(parentId);
                if (parent.getDepartmentChildren() == null) {
                    parent.setDepartmentChildren(new ArrayList<>());
                }
                parent.getDepartmentChildren().add(departmentChildren);
            }
        }

        return tree;
    }
}
