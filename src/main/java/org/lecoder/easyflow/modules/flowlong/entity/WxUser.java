package org.lecoder.easyflow.modules.flowlong.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lecoder.easyflow.common.entity.BaseEntity;

import java.util.List;

/**
 * @author Administrator
 * 企微用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wx_user")
public class WxUser extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 职位
     */
    private String position;

    /**
     * 部门 多个逗号隔开
     */
    private String deptIds;
    /**
     * 是否部门负责人 多个逗号隔开
     */
    private String leaderDept;

    /**
     * 直属上级UserID
     */
    private Long directLeader;

    /**
     * 主部门
     */
    private Integer mainDepartment;
    /**
     * 排序
     */
    private String order;

    /**
     * 激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。
     */
    private Integer status;


}
