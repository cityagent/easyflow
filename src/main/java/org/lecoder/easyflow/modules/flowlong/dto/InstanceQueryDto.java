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
public class InstanceQueryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 审批状态
     * 0，审批中 1，审批通过 2，审批拒绝 3，撤销审批 4，超时结束 5，强制终止',
     */
    private String instanceState;
}
