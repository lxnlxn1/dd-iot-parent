package cn.dreamdeck.user.client;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.model.user.SysUser;
import cn.dreamdeck.user.client.impl.SysUserFeignServiceClientImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "dd-user", fallback = SysUserFeignServiceClientImpl.class)
public interface SysUserFeignService {

    /**
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/query/{userId}")
    DdResult query(@PathVariable("userId") String userId);

    @ApiOperation(value = "返回所有人员Id与姓名/联系方式")
    @GetMapping("/user/getAll")
    DdResult getAll();


    @GetMapping("/user/getUserByIds/{userIds}")
    List<SysUser> getUserByIds(@PathVariable("userIds") String userIds);

    //OA用户同步接口
    @ApiOperation("根据用户ID获取用户信息")
    @GetMapping("/user/getUserById/{userId}")
    SysUser getUserById(@PathVariable("userId") String OldUserId);
}
