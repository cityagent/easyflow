package org.lecoder.easyflow.modules.flowlong.controller;

import com.aizuda.bpm.engine.FlowLongEngine;
import com.aizuda.bpm.engine.core.FlowCreator;
import com.aizuda.bpm.engine.entity.FlwInstance;
import com.aizuda.bpm.engine.entity.FlwTask;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/processTest")
@AllArgsConstructor
public class testController {

    protected FlowLongEngine flowLongEngine;
    // 流程定义服务 flowLongEngine.processService();

    // 流程常用查询服务 flowLongEngine.queryService();

    // 流程任务服务 flowLongEngine.taskService();


    protected static FlowCreator testCreator = FlowCreator.of("test001", "测试001");

    /**
     * 部署流程定义
     */
    @GetMapping("/deploy")
    public Long deployByResource() {
        return flowLongEngine.processService().deployByResource("process.json", testCreator, false);
    }
    /**
     * 启动流程实例
     */
    @GetMapping("/instance-start")
    public FlwInstance instanceStart() {
        Map<String, Object> args = new HashMap<>();
        args.put("day", 8);
        args.put("assignee", "test00100");
        return flowLongEngine.startInstanceByProcessKey("process", null, testCreator, args).get();
    }

    /**
     * 流程
     */
    @GetMapping("/queryProcess")
    public void queryProcess(Long instanceId) {
        List<FlwTask> tasks = flowLongEngine.queryService().getActiveTasksByInstanceId(instanceId).orElseThrow(() -> new RuntimeException("任务不存在"));
        FlwTask flwTask = tasks.stream().filter(t -> Objects.equals("领导审批", t.getTaskName())).findFirst().orElseThrow(() -> new RuntimeException("任务不存在"));

        flowLongEngine.executeTask(flwTask.getId(), FlowCreator.of("test002", "何敏"), Collections.singletonMap("reason", "同意"));

    }
}
