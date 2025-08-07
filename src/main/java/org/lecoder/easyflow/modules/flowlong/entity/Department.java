package org.lecoder.easyflow.modules.flowlong.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lecoder.easyflow.common.entity.BaseEntity;

/**
 * @author Administrator
 * 企微部门
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wx_department")
public class Department extends BaseEntity {

    private static final long serialVersionUID = 1L;
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


}
