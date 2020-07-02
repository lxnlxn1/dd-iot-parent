package cn.dreamdeck.user.synchronization.Oa;


import cn.dreamdeck.model.user.SysUser;
import cn.dreamdeck.service.http.HttpRequest;
import cn.dreamdeck.user.service.SysUserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class AutoProjectByOa {

    public int synchronizationProjectByOa(SysUserService sysUserService, String url) {


        String jsonStrBody = HttpRequest.sendGet(url, null);
        List<RojectRUserVo> receiveList = null;
        int i = 0;
        try {
            JSONObject projectObject = JSONObject.parseObject(jsonStrBody);
            receiveList = (List<RojectRUserVo>) JSONArray.parseArray(projectObject.getString("data"), RojectRUserVo.class);
            List<SysUser> sysUserList = sysUserService.list(new QueryWrapper<SysUser>());
         //   System.out.println("------------" + receiveList.get(0).getUsername());
            if (null != receiveList && receiveList.size() > 0 && null != sysUserList) {

                for (RojectRUserVo rojectRUserVo : receiveList) {
                    boolean status = false;
                    for (SysUser sysUser : sysUserList) {
                        if (sysUser.getOldId() == rojectRUserVo.getUserId()) {
                            status = true;
                            break;
                        }
                    }
                    if (!status) {
                        SysUser sysUser = new SysUser();
                        BeanUtils.copyProperties(rojectRUserVo, sysUser);
                        sysUser.setUserId(null);
                        sysUser.setOldId(rojectRUserVo.getUserId());
                        sysUserService.save(sysUser);
                        ++i;
                    }


                }

            } else {
                i = -1;
            }
        } catch (Exception e) {
            i = -1;
        }
        System.out.println(i);
        System.out.println("执行成功");
        return i;
    }


}
