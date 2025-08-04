package org.lecoder.easyflow.modules.flowlong.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lecoder.easyflow.common.entity.BaseEntity;

/**
 * @author Administrator
 * 申请模版
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flw_approval_template")
public class ApprovalTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 模版名称
     */
    private String name;
    /**
     * 模版分组
     */
    private String templateGroup;

    /**
     * 内容 json
     */
    private String content;

    /**
     * 关联流程ID
     */
    private Long processId;


}
