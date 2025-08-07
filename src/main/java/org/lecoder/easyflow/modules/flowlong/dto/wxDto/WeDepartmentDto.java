package org.lecoder.easyflow.modules.flowlong.dto.wxDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeDepartmentDto {
    private int id;
    private String name;
    private int parentid;
    private int order;
    private List<String> department_leader;
}
