package cn.dreamdeck.iot.controller.iot;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdProjectService;
import cn.dreamdeck.iot.service.DdSyncService;
import cn.dreamdeck.iot.synchronization.Oa.AutoProjectByOa;
import cn.dreamdeck.model.iot.DdSync;
import cn.dreamdeck.user.client.SysUserFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-19
 */

@Api(description = "项目数据同步接口")
@RestController
@RequestMapping("/iot/sync")
public class DdSyncController {

    @Autowired
    private DdProjectService ddProjectService;

    @Autowired
    private DdSyncService syncService;

    @Autowired
    private SysUserFeignService sysUserFeignService;


    AutoProjectByOa autoProjectByOa = new AutoProjectByOa();

    //OA项目同步接口
    @ApiOperation("OA项目同步接口")
    @GetMapping("/synchronizationProjectByOa")
    public DdResult synchronizationProjectByOa() {
        DdSync ddSync = syncService.getById("2");
        int mas = autoProjectByOa.synchronizationProjectByOa(ddProjectService,sysUserFeignService, ddSync.getUrl());
        if (mas != -1) {
            return DdResult.ok("更新成功" + "本次更新" + mas + "条");
        }
        return DdResult.fail("更新失败");
    }

    //同步接口数据回显接口
    @ApiOperation("同步接口数据回显接口")
    @GetMapping("/getSynchronizationUrl/{id}")
    public DdResult getSynchronizationUrl(@PathVariable("id") String id) {
        DdSync ddSync = syncService.getById(id);
        if (null != ddSync) {
            return DdResult.ok(ddSync);
        }
        return DdResult.fail("更新失败");
    }

    //更新项目同步接口
    @ApiOperation("更新项目同步接口")
    @GetMapping("/updateSynchronizationUrl/{id}/{url}")
    public DdResult updateSynchronizationUrl(@PathVariable("id") String id, @PathVariable("url") String url) {
        DdSync ddSync = syncService.getById("1");
        ddSync.setUrl(url);
        ddSync.setUpdateTime(DateUtil.getTime());
        boolean b = syncService.updateById(ddSync);
        if (b) {
            return DdResult.ok("更新成功");
        }
        return DdResult.ok("更新失败");
    }

    //现场项目同步接口
    @ApiOperation("现场版本数据同步接口")
    @GetMapping("/synchronizationProjectByLocale")
    public DdResult synchronizationProjectByLocale() {
        return DdResult.ok("更新失败");
    }


}

