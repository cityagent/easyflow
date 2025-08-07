package org.lecoder.easyflow.modules.flowlong.dto.wxDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeComUserResponse {
    private Integer errcode;
    private String errmsg;
    private List<WxUserDto> dept_user;


}
