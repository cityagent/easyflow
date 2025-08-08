package org.lecoder.easyflow.modules.flowlong.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 申请模版
 */
@Data
@ApiModel("模版参数")
public class ApprovalTemplateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主建")
    private Long id;

    /**
     * 模版名称
     */
    @ApiModelProperty(value = "模版名称")
    private String name;

    /**
     * 模版分组
     */
    @ApiModelProperty(value = "模版分组")
    private String templateGroup;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private List<FiledDto> content;

    /**
     * 关联流程ID
     */
    @ApiModelProperty(value = "关联流程ID")
    private Long processId;


}
