package cn.dreamdeck.trash.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.trash.netty.DdNettyStart;
import cn.dreamdeck.trash.service.DdTrashService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "语音垃圾桶")
@RequestMapping("/trash")
public class TrashController {
    private static final Logger logger = LoggerFactory.getLogger(DdNettyStart.class);

    @Autowired
    private DdTrashService trashService;

    //手动打开垃圾桶R
    @ApiOperation(value = "手动打开垃圾桶")
    @GetMapping(value = "/openTrash/{trashId}/{trashNum}")
    public DdResult openTrash(@PathVariable(value = "trashId", required = true) String trashId, @PathVariable(value = "trashNum", required = true) String trashNum) {
        return DdResult.ok(trashService.openTrash(trashId, trashNum));
    }

    //被打开次数
    @ApiOperation(value = "获取设备被打开次数")
    @GetMapping(value = "/openNum/{trashId}")
    public DdResult openNum(@PathVariable(value = "trashId", required = true) String trashId) {
        return DdResult.ok(trashService.openNum(trashId));
    }

    //状态
    @ApiOperation(value = "获取设备状态")
    @GetMapping(value = "/status/{trashId}")
    public DdResult status(@PathVariable(value = "trashId", required = true) String trashId) {

        String status = trashService.status(trashId);
        if (status == null) return DdResult.ok("设备数据不存在");
        return DdResult.ok(status);
    }


    //状态
    @ApiOperation(value = "获取设备满溢度")
    @GetMapping(value = "/satisfaction/{trashId}")
    public DdResult satisfaction(@PathVariable(value = "trashId", required = true) String trashId) {
        String satisfaction = trashService.satisfaction(trashId);
        if (StringUtils.isEmpty(satisfaction)) {
            return DdResult.fail("状态不存在");
        }
        return DdResult.ok(satisfaction);
    }

}


