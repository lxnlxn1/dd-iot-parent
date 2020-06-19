package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdProjectService;
import cn.dreamdeck.iot.service.DdSyncService;
import cn.dreamdeck.iot.synchronization.Oa.AutoProjectByOa;
import cn.dreamdeck.model.iot.DdSync;
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


    AutoProjectByOa autoProjectByOa = new AutoProjectByOa();

    //OA项目同步接口
    @ApiOperation("OA项目同步接口")
    @GetMapping("/synchronizationProjectByOa")
    public DdResult synchronizationProjectByOa() {
        int mas = autoProjectByOa.synchronizationProjectByOa(ddProjectService);
        if (mas != -1) {
            return DdResult.ok("更新成功" + "本次更新" + mas + "条");
        }
        return DdResult.ok("更新失败");
    }

    //OA项目同步接口
    @ApiOperation("同步接口数据回显接口")
    @GetMapping("/getSynchronizationUrl/{id}")
    public DdResult getSynchronizationUrl(@PathVariable("id") String id) {
        DdSync ddSync = syncService.getById(id);
        if (null == ddSync) {
            return DdResult.ok(ddSync);
        }
        return DdResult.ok("更新失败");
    }

    //更新OA项目同步接口
    @ApiOperation("更新OA项目同步接口")
    @GetMapping("/updateSynchronizationProjectByOa/{url}")
    public DdResult updateSynchronizationProjectByOa(@PathVariable("url") String url) {
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

