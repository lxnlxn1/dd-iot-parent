package cn.dreamdeck.iot.synchronization.Oa;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.iot.service.DdProjectService;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.model.user.SysUser;
import cn.dreamdeck.service.http.HttpRequest;
import cn.dreamdeck.user.client.SysUserFeignService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

public class AutoProjectByOa {


    public int synchronizationProjectByOa(DdProjectService ddProjectService, SysUserFeignService sysUserFeignService, String url) {


        String jsonStrBody = HttpRequest.sendGet(url, null);
        List<RojectRVo> receiveList = null;
        int i = 0;
        try {
            JSONObject projectObject = JSONObject.parseObject(jsonStrBody);
            receiveList = (List<RojectRVo>) JSONArray.parseArray(projectObject.getString("data"), RojectRVo.class);
            List<DdProject> ddProjectList = ddProjectService.list(new QueryWrapper<DdProject>());
            if (null != receiveList && receiveList.size() > 0 && null != ddProjectList) {

                for (RojectRVo rojectRVo : receiveList) {
                    boolean status = false;
                    for (DdProject ddProject : ddProjectList) {
                        if (ddProject.getItemId() == rojectRVo.getItemId()) {
                            status = true;
                            break;
                        }
                    }
                    if (!status) {
                        DdProject ddProject1 = new DdProject();
                        ddProject1.setProjectName(rojectRVo.getAlias());
                        ddProject1.setProjectType(rojectRVo.getType());
                        ddProject1.setProjectAlias(rojectRVo.getName());
                        ddProject1.setProjectType(rojectRVo.getType());
                        ddProject1.setStatus(rojectRVo.getStatus());
                        ddProject1.setProjectImg("http://file.dreamdeck.cn/app/icons//website/logo/2019-05/23/5d3295c4-a8d3-4779-b614-b1a7dac0db09.gif");
                        ddProject1.setProjectSite("项目地址");
                        ddProject1.setUserId(rojectRVo.getUserId());
                        SysUser userById = sysUserFeignService.getUserById(rojectRVo.getUserId().toString());
                        ddProject1.setUserName(userById.getUsername());
                        ddProject1.setCreateTime(DateUtil.getTime());
                        ddProject1.setUpdateTime(DateUtil.getTime());
                        ddProject1.setItemId(rojectRVo.getItemId());
                        ddProject1.setStartDate(rojectRVo.getStartDate());
                        ddProject1.setEndDate(rojectRVo.getEndDate());
                        ddProject1.setDelFlag(rojectRVo.getDelFlag());
                        ddProjectService.save(ddProject1);
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
        System.out.println(receiveList);


        System.out.println("执行成功");
        return i;
    }


}
