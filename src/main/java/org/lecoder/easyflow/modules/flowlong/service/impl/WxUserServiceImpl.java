package org.lecoder.easyflow.modules.flowlong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.modules.flowlong.entity.WxUser;
import org.lecoder.easyflow.modules.flowlong.mapper.WxUserMapper;
import org.lecoder.easyflow.modules.flowlong.service.IWeComUserService;
import org.lecoder.easyflow.modules.flowlong.service.IWxUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
@AllArgsConstructor
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements IWxUserService {

    private final IWeComUserService weComUserService;


    @Override
    public List<WxUser> selectList(WxUser user) {
        return this.getBaseMapper().selectList(user);
    }

    @Override
    public AjaxResult syncUser() {
        List<WxUser> userlist = weComUserService.getUserlist();
        if (!CollectionUtils.isEmpty(userlist)) {
            this.saveOrUpdateBatch(userlist);
        }
        return AjaxResult.success();
    }
}
