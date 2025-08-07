package org.lecoder.easyflow.modules.flowlong.dto.wxDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeComUserDepartmentResponse {

    private Integer errcode;

    private String errmsg;

    private List<WeDepartmentDto> department;


}
