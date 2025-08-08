package org.lecoder.easyflow.modules.flowlong.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.common.page.TableDataInfo;
import org.lecoder.easyflow.modules.flowlong.dto.ApprovalTemplateDto;
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
@Api(tags = "模版配置")
@AllArgsConstructor
public class TemplateController extends BaseController {

    private final IApprovalTemplateService approvalTemplateService;


    /**
     * 获取所有组件类型
     *
     * @return AjaxResult
     */
    @ApiOperation("获取模版所有组件类型")
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
    @ApiOperation("模版列表查询")
    @PostMapping("/list")
    public TableDataInfo list(ApprovalTemplateDto approvalTemplate) {
        startPage();
        List<ApprovalTemplateDto> list = approvalTemplateService.selectList(approvalTemplate);
        return getDataTable(list);
    }

    /**
     * 模版详情
     *
     * @param id id
     * @return AjaxResult
     */
    @ApiOperation("模版详情")
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
    @ApiOperation("模版新增")
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
    @ApiOperation("模版修改")
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
    @ApiOperation("模版删除")
    @DeleteMapping("/remove/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        approvalTemplateService.delete(id);
        return AjaxResult.success();
    }


}
