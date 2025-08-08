package org.lecoder.easyflow.modules.flowlong.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lecoder.easyflow.modules.flowlong.dto.ApprovalTemplateDto;
import org.lecoder.easyflow.modules.flowlong.dto.FiledDto;
import org.lecoder.easyflow.modules.flowlong.entity.ApprovalTemplate;
import org.lecoder.easyflow.modules.flowlong.mapper.ApprovalTemplateMapper;
import org.lecoder.easyflow.modules.flowlong.service.IApprovalTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lijile
 * @since 2021-10-26
 */
@Service
public class ApprovalTemplateServiceImpl extends ServiceImpl<ApprovalTemplateMapper, ApprovalTemplate> implements IApprovalTemplateService {


    @Override
    public List<ApprovalTemplateDto> selectList(ApprovalTemplateDto approvalTemplate) {
        return this.baseMapper.selectList(approvalTemplate);
    }

    @Override
    public int insert(ApprovalTemplateDto approvalTemplate) {
        ApprovalTemplate template = new ApprovalTemplate();
        template.setName(approvalTemplate.getName());
        template.setTemplateGroup(approvalTemplate.getTemplateGroup());
        template.setContent(JSONUtil.toJsonStr(approvalTemplate.getContent()));
        template.setProcessId(approvalTemplate.getProcessId());
        return this.baseMapper.insert(template);
    }

    @Override
    public int update(ApprovalTemplateDto approvalTemplate) {
        if (approvalTemplate.getId() == null) {
            return 0;
        }
        ApprovalTemplate template = new ApprovalTemplate();
        template.setId(approvalTemplate.getId());
        template.setName(approvalTemplate.getName());
        template.setTemplateGroup(approvalTemplate.getTemplateGroup());
        template.setContent(JSONUtil.toJsonStr(approvalTemplate.getContent()));
        template.setProcessId(approvalTemplate.getProcessId());
        return this.baseMapper.updateById(template);
    }

    @Override
    public void delete(Long id) {
        this.baseMapper.deleteById(id);
    }

    @Override
    public ApprovalTemplateDto detail(Long id) {
        ApprovalTemplate template = this.baseMapper.selectById(id);
        ApprovalTemplateDto templateDto = new ApprovalTemplateDto();
        templateDto.setId(template.getId());
        templateDto.setName(template.getName());
        templateDto.setTemplateGroup(template.getTemplateGroup());
        if (!StringUtils.isEmpty(template.getContent())) {
            templateDto.setContent(JSONUtil.parseArray(template.getContent()).toList(FiledDto.class));
        }
        templateDto.setProcessId(template.getProcessId());
        return templateDto;
    }
}
