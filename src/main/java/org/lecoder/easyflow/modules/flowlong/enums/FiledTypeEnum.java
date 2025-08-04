package org.lecoder.easyflow.modules.flowlong.enums;

import lombok.Getter;

/**
 * 用户状态
 * 
 * @author ruoyi
 */
@Getter
public enum FiledTypeEnum
{
    TEXT("text", "文本"), DATE("date", "日期"), NUMBER("number", "数字")
    , RADIOBUTTON("radiobutton", "单选框") , CHECKBOX("checkbox", "单选框")
    ;

    private final String code;
    private final String info;

    FiledTypeEnum(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

}
