package org.lecoder.easyflow.modules.flowlong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.modules.flowlong.dto.DepartmentDto;
import org.lecoder.easyflow.modules.flowlong.service.IDepartmentService;
import org.lecoder.easyflow.modules.flowlong.service.IWxUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/wxUser")
@Api(tags = "企微用户")
@AllArgsConstructor
public class WxUserController {

    private final IWxUserService wxUserService;


    private final IDepartmentService departmentService;

    /**
     * 部门同步
     */
    @ApiOperation("部门同步")
    @PostMapping("/syncDepartment")
    public AjaxResult syncDepartment() {
        return departmentService.syncDepartment();
    }

    /**
     * 员工同步
     */
    @ApiOperation("员工同步")
    @GetMapping("/syncUser")
    public AjaxResult syncUser() {
        return wxUserService.syncUser();
    }

    /**
     * 获取部门树形数据
     */
    @GetMapping("/getDepartmentTree")
    public AjaxResult getDepartmentTree(DepartmentDto departmentDto) {
        return AjaxResult.success(departmentService.getDepartmentTree(departmentDto));
    }

}
