package cn.dreamdeck.lighting.controller;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.lighting.service.DdLightService;
import cn.dreamdeck.model.light.DdLight;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/light")
public class LightController {

    @Autowired
    private DdLightService ddLightService;


    //读取路灯的配置
    @PostMapping("/getConfig")
    public DdResult getConfig(String deviceId) {

        HashMap<String, Object> map = new HashMap<>();

        map.put("deviceId", deviceId);
        map.put("mode", "01");
        map.put("lightValue", "1");
        map.put("maxLeavle", "100");
        map.put("minLeavle", "1");
        map.put("startTime", "10:00");
        map.put("endTime", "11:00");
        return DdResult.ok(map);
    }

    //在路灯的常规模式下设置路灯的亮度
    @PostMapping("/luminance")
    public DdResult luminance(String lightValue, String deviceId) {


        return DdResult.ok().message("成功");

    }



    //设置路灯工作模式，分为常规模式（恒定亮度，mode值：01）和感应模式（感应到人之后亮度发生变化，mode值：00）
    @PostMapping("/workPattern")
    public DdResult workPattern(String mode, String deviceId) {

        if (StringUtils.isEmpty(mode)){

            mode="01";
        }

        DdLight lightId = ddLightService.getOne(new QueryWrapper<DdLight>().eq("light_id", deviceId));

        lightId.setMode(Integer.valueOf(mode));

        ddLightService.updateById(lightId);

        return DdResult.ok().message("成功");

    }


    //设置路灯照明的时间段，24小时时间制，超过24点为第二天时间
    @PostMapping("/time")
    public DdResult time(String startTime, String endTime, String deviceId) {

        return DdResult.ok().message("成功");
    }


    //设置路灯感应模式下有人亮度和无人亮
    @PostMapping("/someoneBrightness")
    public DdResult someoneBrightness(String maxLeavle, String minLeavle, String deviceId) {

        return DdResult.ok().message("成功");
    }


    //返回LoRa无线网关设备列表
    @PostMapping("/index")
    public DdResult index(String Channel, String deviceId) {

        HashMap<String, Object> map = new HashMap<>();

        map.put("cmd", "state");
        map.put("output", "11111111");
        map.put("input", "00000000");
        map.put("vol", "[2286,2286,2282,2282,2285,2285,2286,2286]");
        map.put("freq", "[4998,4998,4998,4998,4998,4998,4998,4998]");
        map.put("cur", "[751,1378,768,302,846,669,0,0]");
        map.put("factor", "[99,99,85,54,90,76,100,100]");
        map.put("ele", "[8281,23156,12863,5089,14001,7997,0,0]");
        map.put("powerflag", "1");
        map.put("zmsensor", "[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]");
        map.put("eleset", "[0,0,0,0,0,0,0,0]");
        map.put("tset", "[0,0,0,0,0,0,0,0]");
        map.put("paytype", "00000000");
        map.put("topcur", "[0,0,0,0,0,0,0,0]");

        map.put("overflag", "00000000");
        map.put("timestamp", "1578477529");
        map.put("sn", "p8600e020af36140");
        return DdResult.ok(map).message("成功");
    }


    //查询路灯
    @ApiOperation(value = "分页路灯(带条件)")
    @PostMapping("/getightPageVo")
    public DdResult getTeacherPageVo(String current, String limit, String keyword) {
        Long current1 = null;
        Long limit1 = null;

        try {
            current1 = Long.valueOf(current);
            limit1 = Long.valueOf(limit);
        } catch (NumberFormatException e) {

            current1 = 0L;
            limit1 = 0L;


        }

        Page<DdLight> page = new Page<>(current1, limit1);

        QueryWrapper<DdLight> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like("name", keyword);
        }
        //1.5根据创建时间倒排序
        wrapper.orderByDesc("light_id");
        //2调方法获取数据
        ddLightService.page(page, wrapper);
//        //3 从page获取数据
//        long total = page.getTotal();
//        List<DdLight> records = page.getRecords();
        //4 返回数据
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("total",total);
//        data.put("items",records);
//        return R.ok().data(data);
        return DdResult.ok(page);
    }

    //路灯的开关控制，路灯的用电量查询
    //查询路灯
    @ApiOperation(value = "打开路灯")
    @GetMapping("/open")
    public DdResult opne(String id) {
        Long id1 = null;

        try {
            id1 = Long.valueOf(id);
        } catch (NumberFormatException e) {

            if (id1 == 0){
                return DdResult.fail("Id为空");
            }
        }

        DdLight ddLight = ddLightService.getOne(new QueryWrapper<DdLight>().eq("light_id", id1));

        ddLight.setStatus(1);
        ddLight.setLastTime(DateUtil.getDay());
        boolean b = ddLightService.updateById(ddLight);
        return DdResult.ok(b);
    }

    @ApiOperation(value = "关闭路灯")
    @GetMapping("/close")
    public DdResult close(String id) {

        Long id1 = null;

        try {
            id1 = Long.valueOf(id);
        } catch (NumberFormatException e) {

            if (id1 == 0){
                return DdResult.fail("Id为空");
            }
        }

        DdLight ddLight = ddLightService.getOne(new QueryWrapper<DdLight>().eq("light_id", id1));

        ddLight.setStatus(0);
        ddLight.setLastTime(DateUtil.getDay());
        boolean b = ddLightService.updateById(ddLight);
        return DdResult.ok(b);
    }

    //路灯电量查询
    @ApiOperation(value = "电量查询")
    @GetMapping("/getElectricity")
    public DdResult getElectricity(String id) {

        Long id1 = null;

        try {
            id1 = Long.valueOf(id);
        } catch (NumberFormatException e) {

            if (id1 == 0){
                return DdResult.fail("Id为空");
            }


        }
        DdLight light = ddLightService.getOne(new QueryWrapper<DdLight>().eq("light_id", id1));
//
//        if (light.getLastElectricity()-light.getInitialElectricity()<0){
//            return DdResult.fail("设备异常");
//        }

        if (null==light){
            return DdResult.fail("设备不存在");
        }

        return DdResult.ok(light.getLastElectricity()-light.getInitialElectricity());
    }

}
