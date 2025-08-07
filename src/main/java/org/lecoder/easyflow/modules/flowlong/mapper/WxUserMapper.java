package org.lecoder.easyflow.modules.flowlong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lecoder.easyflow.modules.flowlong.entity.WxUser;

import java.util.List;

/**
 * <p>
 * 企微部门
 * </p>
 *
 * @author lh
 */
public interface WxUserMapper extends BaseMapper<WxUser> {

    List<WxUser> selectList(WxUser user);
}
