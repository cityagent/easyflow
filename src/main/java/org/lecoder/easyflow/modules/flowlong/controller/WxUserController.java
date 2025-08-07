package org.lecoder.easyflow.modules.flowlong.controller;

import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.entity.AjaxResult;
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
@AllArgsConstructor
public class WxUserController {

    private final IWxUserService wxUserService;


    private final IDepartmentService departmentService;

    /**
     * 部门同步
     */
    @PostMapping("/syncDepartment")
    public AjaxResult syncDepartment() {
        return departmentService.syncDepartment();
    }

    /**
     * 员工同步
     */
    @GetMapping("/syncUser")
    public AjaxResult syncUser() {
        wxUserService.syncUser();
        return AjaxResult.success();
    }

}
