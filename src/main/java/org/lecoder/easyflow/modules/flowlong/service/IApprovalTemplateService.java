package org.lecoder.easyflow.modules.flowlong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lecoder.easyflow.modules.flowlong.dto.ApprovalTemplateDto;
import org.lecoder.easyflow.modules.flowlong.entity.ApprovalTemplate;

import java.util.List;

/**
 * <p>
 * 申请模版 服务类
 * </p>
 *
 * @author lijile
 * @since 2021-10-26
 */
public interface IApprovalTemplateService extends IService<ApprovalTemplate> {


    List<ApprovalTemplateDto> selectList(ApprovalTemplateDto approvalTemplate);

    // 保存审批模板
    int insert(ApprovalTemplateDto approvalTemplate);

    int update(ApprovalTemplateDto approvalTemplate);

    void delete(Long id);

    ApprovalTemplateDto detail(Long id);
}
