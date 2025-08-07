package org.lecoder.easyflow.modules.flowlong.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lecoder.easyflow.common.entity.BaseEntity;

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


}
