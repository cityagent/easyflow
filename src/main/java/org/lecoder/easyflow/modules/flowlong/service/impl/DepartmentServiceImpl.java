package org.lecoder.easyflow.modules.flowlong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.modules.flowlong.dto.DepartmentDto;
import org.lecoder.easyflow.modules.flowlong.entity.Department;
import org.lecoder.easyflow.modules.flowlong.entity.WxUser;
import org.lecoder.easyflow.modules.flowlong.mapper.DepartmentMapper;
import org.lecoder.easyflow.modules.flowlong.service.IDepartmentService;
import org.lecoder.easyflow.modules.flowlong.service.IWeComUserService;
import org.lecoder.easyflow.modules.flowlong.service.IWxUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
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

    private final IWxUserService wxUserService;

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

    /**
     * 结构处理
     *
     * @param list list
     * @return list
     */
    public List<DepartmentDto> buildTree(List<DepartmentDto> list) {
        // 防御性检查：避免空列表导致的后续异常
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        // 1. 预构建部门ID到部门对象的映射
        Map<Long, DepartmentDto> deptMap = list.stream()
                .collect(Collectors.toMap(
                        DepartmentDto::getId,
                        // 提前初始化子部门列表，避免后续频繁判空
                        dept -> {
                            dept.setDepartmentChildren(new ArrayList<>());
                            return dept;
                        },
                        // 处理ID重复的情况（如果存在）
                        (existing, replacement) -> existing
                ));

        // 2. 批量查询用户并按部门ID分组（
        Map<String, List<WxUser>> userByDeptMap = getUserMapByDept(deptMap.keySet());

        // 3. 构建树形结构并关联用户
        List<DepartmentDto> rootDeptList = new ArrayList<>();
        list.forEach(dept -> {
            Long deptId = dept.getId();
            Long parentId = dept.getParentId();

            // 关联当前部门的用户
            List<WxUser> users = userByDeptMap.get(String.valueOf(deptId));
            Optional.ofNullable(users).ifPresent(dept::setUserList);

            // 处理父子关系
            if (parentId == null || parentId == 0 || !deptMap.containsKey(parentId)) {
                rootDeptList.add(dept);
            } else {
                deptMap.get(parentId).getDepartmentChildren().add(dept);
            }
        });

        return rootDeptList;
    }

    /**
     * 按部门ID集合查询并分组用户
     *
     * @param deptIds 系统中存在的部门ID集合
     * @return 部门ID（字符串）到用户列表的映射
     */
    private Map<String, List<WxUser>> getUserMapByDept(Set<Long> deptIds) {
        if (deptIds == null || deptIds.isEmpty()) {
            return Collections.emptyMap();
        }

        // 转换部门ID为字符串集合（用于后续过滤）
        Set<String> deptIdStr = deptIds.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        // 查询所有用户（可优化为按部门ID过滤查询，减少数据传输）
        List<WxUser> userList = wxUserService.selectList(new WxUser());

        // 按部门ID分组，仅保留存在于系统中的部门
        return userList.stream()
                .filter(user -> user.getDeptIds() != null && !user.getDeptIds().trim().isEmpty())
                .flatMap(user -> {
                    // 拆分部门ID并过滤掉不存在的部门
                    String[] deptIdsArr = user.getDeptIds().split(",");
                    return Arrays.stream(deptIdsArr)
                            .filter(deptIdStr::contains) // 只保留系统中存在的部门
                            .map(deptId -> new AbstractMap.SimpleEntry<>(deptId, user));
                })
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toList())
                ));
    }
}
