package org.lecoder.easyflow.modules.flowlong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lecoder.easyflow.modules.flowlong.entity.ApprovalTemplate;

import java.util.List;

/**
 * <p>
 * 申请模版
 * </p>
 *
 * @author lijile
 * @since 2021-10-25
 */
public interface ApprovalTemplateMapper extends BaseMapper<ApprovalTemplate> {

    List<ApprovalTemplate> selectList(ApprovalTemplate approvalTemplate);
}
