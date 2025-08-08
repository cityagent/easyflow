package org.lecoder.easyflow.modules.flowlong.dto;

import lombok.Data;
import org.lecoder.easyflow.modules.flowlong.entity.WxUser;
import org.simpleframework.xml.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 企微部门
 */
@Data
public class DepartmentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 父级部门ID
     */
    private Long parentId;

    /**
     * 排序规则 最大在最前面
     */
    private int sort;

    /**
     * 部门负责人 多个逗号隔开
     */
    private String departmentLeader;
    /**
     *  员工
     */
    private List<WxUser> userList;

    /**
     * 子节点集合
     */
    @Transient
    private List<DepartmentDto> departmentChildren;
}
