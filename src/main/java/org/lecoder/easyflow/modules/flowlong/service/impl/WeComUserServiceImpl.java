package org.lecoder.easyflow.modules.flowlong.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.lecoder.easyflow.common.config.WeComConfig;
import org.lecoder.easyflow.common.utils.HttpClientUtils;
import org.lecoder.easyflow.modules.flowlong.dto.wxDto.*;
import org.lecoder.easyflow.modules.flowlong.entity.Department;
import org.lecoder.easyflow.modules.flowlong.entity.WxUser;
import org.lecoder.easyflow.modules.flowlong.service.IWeComTokenService;
import org.lecoder.easyflow.modules.flowlong.service.IWeComUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class WeComUserServiceImpl implements IWeComUserService {

    private static final Logger log = LoggerFactory.getLogger(WeComUserServiceImpl.class);
    @Resource
    WeComConfig weComConfig;
    @Resource
    IWeComTokenService weComTokenService;


    // 注入普通字符串
    @Value("${wecom-config.com-credentials.contact-corpsecret}")
    private String secret;

    public String getUrl(String methodName) {
        return weComConfig.getBaseUrl() + weComConfig.getEndpoints().get(methodName);
    }

    @Override
    public List<WxUser> getUserlist() {
        //获取所有用户ID
        String url = getUrl("get-user-list-id");
        Map<String, Object> fetchData = HttpClientUtils.fetchData(weComTokenService.getToken(secret), new HashMap<>(), url, HttpMethod.GET, null);
        ObjectMapper mapper = new ObjectMapper();
        WeComUserResponse weComUserDepartmentResponse = mapper.convertValue(fetchData, WeComUserResponse.class);
        if (weComUserDepartmentResponse.getErrcode() != 0) {
            log.error("获取用户列表失败，错误码：{}，错误信息：{}", weComUserDepartmentResponse.getErrcode(), weComUserDepartmentResponse.getErrmsg());
            throw new RuntimeException("获取用户列表失败");
        }
        List<WxUserDto> deptUser = weComUserDepartmentResponse.getDept_user();
        Map<String, List<WxUserDto>> userMap = deptUser.stream().collect(Collectors.groupingBy(WxUserDto::getUserid));
        //根据用户ID获取用户具体信息
        String token = weComTokenService.getToken(null);
        String urlInfoStr = getUrl("get-user-info");
        ArrayList<WxUser> users = new ArrayList<>();
        List<String> userIds = deptUser.stream().map(WxUserDto::getUserid).distinct().limit(10).collect(Collectors.toList());
        userIds.parallelStream().forEach(userId -> {
            Map<String, Object> param = new HashMap<>();
            param.put("userid", userId);
            Map<String, Object> result = HttpClientUtils.fetchData(token, param, urlInfoStr, HttpMethod.GET, null);
            ObjectMapper objectMapper = new ObjectMapper();
            WeComUserInfoDto weComUserInfoDto = objectMapper.convertValue(result, WeComUserInfoDto.class);
            if (weComUserInfoDto.getErrcode() == 0) {
                WxUser wxUser = new WxUser();
                wxUser.setId(Long.valueOf(weComUserInfoDto.getUserid()));
                wxUser.setName(weComUserInfoDto.getName());
                wxUser.setPosition(weComUserInfoDto.getPosition());
                wxUser.setPhone(weComUserInfoDto.getTelephone());
                if (CollectionUtil.isNotEmpty(weComUserInfoDto.getOrder())){
                    wxUser.setOrder(weComUserInfoDto.getOrder().stream().map(String::valueOf).collect(Collectors.joining(",")));
                }
                wxUser.setMainDepartment(weComUserInfoDto.getMain_department());
                if (CollectionUtil.isNotEmpty(weComUserInfoDto.getDirect_leader())){
                    wxUser.setDirectLeader(weComUserInfoDto.getDirect_leader().get(0));
                }
                wxUser.setStatus(weComUserInfoDto.getStatus());
                if (CollectionUtil.isNotEmpty(weComUserInfoDto.getIs_leader_in_dept())){
                    wxUser.setLeaderDept(weComUserInfoDto.getIs_leader_in_dept().stream().map(String::valueOf).collect(Collectors.joining(",")));
                }
                if (userMap.containsKey(userId)) {
                    List<WxUserDto> userList = userMap.get(userId);
                    if (userList.size() > 1) {
                        List<String> collect = userList.stream().map(WxUserDto::getDepartment).collect(Collectors.toList());
                        wxUser.setDeptIds(StringUtils.join(collect, ","));
                    } else {
                        wxUser.setDeptIds(String.valueOf(userList.get(0).getDepartment()));
                    }
                }
                users.add(wxUser);
            }
        });

        return users;
    }


    @Override
    public List<Department> getDeptList() {
        String url = getUrl("get-dept-list-p");
        Map<String, Object> request = new HashMap<>();
        Map<String, Object> response = HttpClientUtils.fetchData(weComTokenService.getToken(null), request, url, HttpMethod.GET, null);
        ObjectMapper mapper = new ObjectMapper();
        WeComUserDepartmentResponse result = mapper.convertValue(response, WeComUserDepartmentResponse.class);
        if (result.getErrcode().equals(0)) {
            ArrayList<Department> departments = new ArrayList<>();
            result.getDepartment().forEach(dept -> {
                Department department = new Department();
                department.setId(Long.valueOf(dept.getId()));
                department.setSort(dept.getOrder());
                department.setName(dept.getName());
                if (CollectionUtil.isNotEmpty(dept.getDepartment_leader())) {
                    String leader = String.join(",", dept.getDepartment_leader());
                    department.setDepartmentLeader(leader);
                }
                department.setParentId(Long.valueOf(dept.getParentid()));
                departments.add(department);
            });
            return departments;
        } else {
            log.error("获取部门列表失败，错误码：{}，错误信息：{}", result.getErrcode(), result.getErrmsg());
            throw new RuntimeException("获取部门列表失败");
        }
    }
}
