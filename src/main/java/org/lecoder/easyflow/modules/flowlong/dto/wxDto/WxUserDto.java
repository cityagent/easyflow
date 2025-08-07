package org.lecoder.easyflow.modules.flowlong.dto.wxDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Administrator
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WxUserDto {
    private String userid;

    private String department;
}
