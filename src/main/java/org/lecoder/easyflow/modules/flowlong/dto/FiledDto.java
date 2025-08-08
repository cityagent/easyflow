package org.lecoder.easyflow.modules.flowlong.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.lecoder.easyflow.common.utils.DbColumnNameGenerator;
import org.lecoder.easyflow.modules.flowlong.enums.FiledTypeEnum;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel("字段参数")
public class FiledDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 字段名称
     */
    @ApiModelProperty(value = "字段名称")
    private String filedName;

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段")
    private String filed;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private FiledTypeEnum filedType;
    /**
     * 提示内容
     */
    @ApiModelProperty(value = "提示内容")
    private String promptContent;

    /**
     * 是否必填
     */
    @ApiModelProperty(value = "是否必填")
    private Boolean needRequired;

    /**
     * 是否参与打印
     */
    @ApiModelProperty(value = "是否参与打印")
    private Boolean needPrint;

    public void setFiled(String filed) {
        if (this.getFiled()==null){
            this.filed = DbColumnNameGenerator.generateColumnName("t_",this.getFiledName());
        }
    }
}
