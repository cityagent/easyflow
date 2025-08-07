package org.lecoder.easyflow.modules.flowlong.controller;

import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.common.page.TableDataInfo;
import org.lecoder.easyflow.modules.flowlong.dto.ApprovalTemplateDto;
import org.lecoder.easyflow.modules.flowlong.entity.ApprovalTemplate;
import org.lecoder.easyflow.modules.flowlong.enums.FiledTypeEnum;
import org.lecoder.easyflow.modules.flowlong.service.IApprovalTemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * <p>
 * 模版创建
 */
@RestController
@RequestMapping("/template")
@AllArgsConstructor
public class TemplateController extends BaseController {

    private final IApprovalTemplateService approvalTemplateService;


    /**
     * 获取所有组件类型
     *
     * @return AjaxResult
     */
    @PostMapping("/findComponentAll")
    public AjaxResult findComponentAll() {
        List<String> codes = FiledTypeEnum.getAllFiledTypeCodes();
        return AjaxResult.success(codes);
    }

    /**
     * 模版列表查询
     *
     * @param approvalTemplate 模版参数
     * @return page
     */
    @PostMapping("/list")
    public TableDataInfo list(ApprovalTemplate approvalTemplate) {
        startPage();
        List<ApprovalTemplate> list = approvalTemplateService.selectList(approvalTemplate);
        return getDataTable(list);
    }

    /**
     * 模版详情
     *
     * @param id id
     * @return AjaxResult
     */
    @GetMapping("/detail/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return AjaxResult.success(approvalTemplateService.detail(id));
    }

    /**
     * 模版新增
     *
     * @param approvalTemplate 模版参数
     * @return AjaxResult
     */
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody ApprovalTemplateDto approvalTemplate) {
        approvalTemplateService.insert(approvalTemplate);
        return AjaxResult.success();
    }

    /**
     * 模版修改
     *
     * @param approvalTemplate 模版参数
     * @return AjaxResult
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody ApprovalTemplateDto approvalTemplate) {
        approvalTemplateService.update(approvalTemplate);
        return AjaxResult.success();
    }

    /**
     * 模版删除
     *
     * @param id id
     * @return AjaxResult
     */
    @DeleteMapping("/remove/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        approvalTemplateService.delete(id);
        return AjaxResult.success();
    }


}
