package org.lecoder.easyflow.modules.flowlong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lecoder.easyflow.common.entity.AjaxResult;
import org.lecoder.easyflow.modules.flowlong.entity.WxUser;

import java.util.List;

/**
 * <p>
 * 企微用户接口
 * </p>
 *
 * @author lijile
 * @since 2021-10-26
 */
public interface IWxUserService extends IService<WxUser> {


    /**
     * 查询列表
     *
     * @param user 参数
     * @return 列表
     */
    List<WxUser> selectList(WxUser user);


    /**
     * 企微微信部门 同步
     *
     * @return AjaxResult
     */
    AjaxResult syncUser();


}
