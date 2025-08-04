package org.lecoder.easyflow.modules.flowlong.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 申请模版
 */
@Data
public class ApprovalTemplateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 模版名称
     */
    private String name;

    /**
     * 模版分组
     */
    private String templateGroup;
    /**
     * 内容
     */
    private List<FiledDto> content;

    /**
     * 关联流程ID
     */
    private Long processId;


}
