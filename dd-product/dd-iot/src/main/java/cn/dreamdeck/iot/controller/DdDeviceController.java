package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.model.iot.vo.DdDeviceVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 所有设备列表 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */

@Api(description = "设备管理")
@RestController
@RequestMapping("/iot/device")
public class DdDeviceController {

    //查询设备
    @ApiOperation(value = "根据项目id，查询查询设备（分页，查询）")
    @PostMapping("/getevicePageVo")
    public DdResult getTeacherPageVo(String current, String limit, DdDeviceVo ddDeviceVo) {

        Long current1 = null;
        Long limit1 = null;

        try {
            current1 = Long.valueOf(current);
            limit1 = Long.valueOf(limit);
        } catch (NumberFormatException e) {

            current1 = 0L;
            limit1 = 5L;


        }
        Page<DdProject> page = new Page<>(current1, limit1);



        return DdResult.ok();
    }


}

