package cn.dreamdeck.trash.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.trash.netty.DdNettyStart;
import cn.dreamdeck.trash.service.DdTrashService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/trash")
public class TrashController {
    private static final Logger logger = LoggerFactory.getLogger(DdNettyStart.class);

    @Autowired
    private DdTrashService trashService;

    //手动打开垃圾桶R
    @ApiOperation(value = "手动打开垃圾桶")
   @GetMapping(value = "/openTrash")
    public DdResult openTrash(String ip, String num) {

        return DdResult.ok(trashService.openTrash(ip, num));
    }

    //被打开次数
    @ApiOperation(value = "获取设备被打开次数")
    @GetMapping(value = "/openNum")
    @ResponseBody
    public DdResult openNum(String ip) {

        Map<String,Integer> map = trashService.openNum(ip);
        if (map.size()==0){
            return DdResult.ok("数据不存在");
        }
        return DdResult.ok(map);
    }

    //状态
    @ApiOperation(value = "获取设备状态")
    @GetMapping(value = "/status")
    public DdResult status(String ip) {

        String status = trashService.status(ip);
        if (status==null)        return DdResult.ok("设备数据不存在");
        return DdResult.ok(status);
    }


}


