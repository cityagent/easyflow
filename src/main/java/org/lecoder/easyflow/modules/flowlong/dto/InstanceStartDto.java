package org.lecoder.easyflow.modules.flowlong.dto;

import com.aizuda.bpm.engine.model.NodeAssignee;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Data
public class InstanceStartDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    private Long taskId;
    /**
     * 流程实例ID
     */
    private Long instanceId;
    /**
     * 流程key
     */
    private String processKey;


    /**
     * 审批数据
     */
    private Map<String, Object> args;


    /**
     * 节点处理人 key 流程节点 value 处理人
     */
    private Map<String, List<NodeAssignee>> nodeAssigneeMap;
}
