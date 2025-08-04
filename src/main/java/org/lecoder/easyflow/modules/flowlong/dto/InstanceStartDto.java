package org.lecoder.easyflow.modules.flowlong.dto;

import lombok.Data;

import java.io.Serializable;
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

    private Map<String, Object> args;
}
