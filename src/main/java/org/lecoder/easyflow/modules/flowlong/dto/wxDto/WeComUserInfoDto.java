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

    /**
     * 表示在所在的部门内是否为部门负责人 1是 0 否
     */
    private List<Integer> is_leader_in_dept;

    /**
     * 直属上级UserID
     */
    private List<Long> direct_leader;

    /**
     * 主部门
     */
    private Integer main_department;
    /**
     * 排序
     */
    private List<Long> order;

    /**
     * 激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。
     */
    private Integer status;


}
