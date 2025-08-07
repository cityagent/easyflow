package org.lecoder.easyflow.modules.flowlong.dto.wxDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeComUserInfoDto {

    private Integer errcode;

    private String errmsg;

    private String userid;

    private List<String> department;

    private String name;

    private String telephone;

    private String position;


}
