package org.lecoder.easyflow.modules.flowlong.dto;

import lombok.Data;
import org.lecoder.easyflow.modules.flowlong.enums.FiledTypeEnum;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class FiledDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 字段名称
     */
    private String filedName;

    /**
     * 字段
     */
    private String filed;
    /**
     * 类型
     */
    private FiledTypeEnum filedType;
    /**
     * 提示内容
     */
    private String promptContent;

    /**
     * 是否必填
     */
    private Boolean needRequired;

    /**
     * 是否参与打印
     */
    private Boolean needPrint;
}
