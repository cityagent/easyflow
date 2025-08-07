package org.lecoder.easyflow.modules.flowlong.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.aizuda.bpm.engine.FlowDataTransfer;
import com.aizuda.bpm.engine.FlowLongEngine;
import com.aizuda.bpm.engine.core.FlowLongContext;
import com.aizuda.bpm.engine.entity.FlwHisInstance;
import com.aizuda.bpm.engine.entity.FlwInstance;
import com.aizuda.bpm.engine.entity.FlwProcess;
import com.aizuda.bpm.engine.entity.FlwTask;
import com.aizuda.bpm.engine.model.DynamicAssignee;
import com.aizuda.bpm.mybatisplus.mapper.FlwHisInstanceMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.modules.flowlong.dto.InstanceStartDto;
import org.lecoder.easyflow.modules.flowlong.dto.ProcessModelDto;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * 流程定义
 */
@RestController
@RequestMapping("/process")
@AllArgsConstructor
public class ProcessController extends BaseController {

    protected FlowLongEngine flowLongEngine;

    private final FlwHisInstanceMapper flwHisInstanceMapper;


    /**
     * 创建流程
     */
    @PostMapping("/deploy")
    public AjaxResult deployByResource(@RequestBody ProcessModelDto processModel) {
        String modelContent = FlowLongContext.toJson(processModel);
        // 将 String 转换为 InputStream（使用 UTF-8 编码）
        InputStream inputStream = new ByteArrayInputStream(modelContent.getBytes(StandardCharsets.UTF_8));
        return AjaxResult.success(flowLongEngine.processService().deploy(inputStream, getFlowCreator(), false));
    }

    /**
     * 查询自己已提交的所有任务
     */
    @PostMapping("/instanceQuery")
    public AjaxResult instanceQuery(@RequestBody InstanceStartDto instanceStartDto) {
        LambdaQueryWrapper<FlwHisInstance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FlwHisInstance::getCreateId, "test001");
        List<FlwHisInstance> flwHisInstances = flwHisInstanceMapper.selectList(wrapper);
        return AjaxResult.success(flwHisInstances);
    }

    /**
     * 查询我审批的 所有任务 包含  待处理 已处理  抄送我 状态
     */
    @PostMapping("/instanceAuditQuery")
    public AjaxResult instanceAuditQuery(@RequestBody InstanceStartDto instanceStartDto) {

        return AjaxResult.success();
    }

    /**
     * 发起任务
     */
    @PostMapping("/instanceStart")
    public AjaxResult instanceStart(@RequestBody InstanceStartDto instanceStartDto) {
        if (instanceStartDto.getProcessKey() == null) {
            throw new RuntimeException("流程key不能为空");
        }
        FlwProcess process = flowLongEngine.processService().getProcessByKey(null, instanceStartDto.getProcessKey());
        if (process != null) {
            if (CollectionUtil.isNotEmpty(instanceStartDto.getNodeAssigneeMap())) {
                HashMap<String, Object> map = new HashMap<>();
                instanceStartDto.getNodeAssigneeMap().forEach((nodeKey, nodeAssigneeList) -> {
                    map.put(nodeKey, DynamicAssignee.assigneeUserList(nodeAssigneeList));
                });
                FlowDataTransfer.dynamicAssignee(map);
            }
            FlwInstance flwInstance = flowLongEngine.startInstanceByProcessKey(instanceStartDto.getProcessKey(), process.getProcessVersion(), getFlowCreator(), instanceStartDto.getArgs()).orElseGet(() -> null);
            return AjaxResult.success(flwInstance);
        } else {
            throw new RuntimeException("流程不存在");
        }

    }

    /**
     * 撤回任务
     */
    @GetMapping("/withdrawTask")
    public AjaxResult withdrawTask(Long taskId) {
        flowLongEngine.taskService().withdrawTask(taskId, getFlowCreator());
        return AjaxResult.success();
    }


    /**
     * 审批同意
     */
    @PostMapping("/withdrawTask")
    public AjaxResult executeTask(@RequestBody InstanceStartDto instanceStartDto) {
        FlwTask flwTask = flowLongEngine.queryService().getTask(instanceStartDto.getTaskId());
        flowLongEngine.executeTask(flwTask.getId(), getFlowCreator(), instanceStartDto.getArgs());
        return AjaxResult.success();
    }


    /**
     * 审批拒绝
     */
    @PostMapping("/executeRejectTask")
    public AjaxResult executeRejectTask(@RequestBody InstanceStartDto instanceStartDto) {
        FlwTask flwTask = flowLongEngine.queryService().getTask(instanceStartDto.getTaskId());
        flowLongEngine.executeRejectTask(flwTask, getFlowCreator(), instanceStartDto.getArgs());
        return AjaxResult.success();
    }
}
