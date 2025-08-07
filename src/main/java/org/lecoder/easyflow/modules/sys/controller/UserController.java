package org.lecoder.easyflow.modules.sys.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.lecoder.easyflow.common.toolkit.NetworkUtils;
import org.lecoder.easyflow.common.toolkit.RequestHolder;
import org.lecoder.easyflow.common.web.CommonResult;
import org.lecoder.easyflow.modules.flowlong.controller.BaseController;
import org.lecoder.easyflow.modules.sys.constant.CacheConsts;
import org.lecoder.easyflow.modules.sys.dto.LoginFormDTO;
import org.lecoder.easyflow.modules.sys.dto.LoginUserDTO;
import org.lecoder.easyflow.modules.sys.dto.UserDTO;
import org.lecoder.easyflow.modules.sys.entity.SysUser;
import org.lecoder.easyflow.modules.sys.service.ISysUserService;
import org.lecoder.easyflow.modules.sys.vo.SearchUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户模块
 *
 * @author: lijile
 * @date: 2021/10/26 17:24
 * @version: 1.0
 */
@Api(tags = "sys.UserController")
@RestController
@RequestMapping("/sys/user")
@AllArgsConstructor
public class UserController extends BaseController {

    private final ISysUserService userService;

    private final CacheManager cacheManager;

    /**
     * 获取当前信息
     *
     * @return
     * @author: lijile
     * @date: 2021/10/27 10:40
     */
    @ApiOperation("获取当前信息")
    @GetMapping("/currentUser")
    public CommonResult queryCurrentUser() {
        return CommonResult.success(RequestHolder.getCurrentUser());
    }

    /**
     * 用户登录
     *
     * @param loginFormDTO
     * @return
     * @author: lijile
     * @date: 2021/10/27 9:40
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult login(@Valid @RequestBody LoginFormDTO loginFormDTO) {
        UserDTO userDTO = userService.login(loginFormDTO);
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        BeanUtils.copyProperties(userDTO, loginUserDTO);

        String token = "token_" + RandomUtil.randomString(50);
        Cache cache = cacheManager.getCache(CacheConsts.USER_TOKEN);
        cache.put(token, userDTO);

        loginUserDTO.setToken(token);
        return CommonResult.success(loginUserDTO);
    }

    /**
     * 退出登录
     *
     * @return
     * @author: lijile
     * @date: 2021/11/4 15:59
     */
    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public CommonResult logout(HttpServletRequest request) {
        String token = NetworkUtils.getSessionToken(request);
        if (token != null) {
            Cache cache = cacheManager.getCache(CacheConsts.USER_TOKEN);
            cache.evictIfPresent(token);
        }
        return CommonResult.success();
    }

    /**
     * 用户搜索
     *
     * @param keyword
     * @return
     * @author: lijile
     * @date: 2021/11/3 15:50
     */
    @ApiOperation("用户搜索")
    @GetMapping("/search")
    public CommonResult search(String keyword) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            queryWrapper.likeRight(SysUser::getFullname, keyword);
        }
        queryWrapper.last("limit 10");
        List<SysUser> sysUserList = userService.list(queryWrapper);
        List<SearchUserVO> userList = sysUserList.stream().map(this::convert2SearchUser).collect(Collectors.toList());
        return CommonResult.success(userList);
    }

    private SearchUserVO convert2SearchUser(SysUser user) {
        SearchUserVO searchUserVO = new SearchUserVO();
        BeanUtils.copyProperties(user, searchUserVO);
        return searchUserVO;
    }
    /**
     * 用户保存
     * @param userDTO userDTO
     */
    @ApiOperation("用户保存")
    @PostMapping("/save")
    public CommonResult save(@RequestBody UserDTO userDTO) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(userDTO.getUsername());
        sysUser.setPassword("e10adc3949ba59abbe56e057f20f883e");
        sysUser.setFullname(userDTO.getFullname());
        sysUser.setPhone(userDTO.getPhone());
        sysUser.setEmail(userDTO.getEmail());
        sysUser.setDept(userDTO.getDept());
        sysUser.setPosition(userDTO.getPosition());
        userService.save(sysUser);
        return CommonResult.success(sysUser);
    }


}
