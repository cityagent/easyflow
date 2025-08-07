package org.lecoder.easyflow.modules.flowlong.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户状态
 *
 * @author ruoyi
 */
@Getter
public enum FiledTypeEnum {
    TEXT("TEXT", "文本"),
    DATE("DATE", "日期"),
    NUMBER("NUMBER", "数字"),
    RADIOBUTTON("RADIOBUTTON", "单选框"),
    CHECKBOX("CHECKBOX", "多选框");

    private final String code;
    private final String info;

    FiledTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static List<String> getAllFiledTypeCodes() {
        return Arrays.stream(FiledTypeEnum.values()).map(FiledTypeEnum::getCode).collect(Collectors.toList());
    }

}
