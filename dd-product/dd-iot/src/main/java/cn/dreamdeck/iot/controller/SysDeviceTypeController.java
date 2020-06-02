package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.SysDeviceTypeService;
import cn.dreamdeck.iot.service.SysDictDataService;
import cn.dreamdeck.model.iot.SysDeviceType;
import cn.dreamdeck.model.iot.SysDictData;
import cn.dreamdeck.model.iot.vo.SysDeviceTypeModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("sysDevicType")
@Api(value = "sysDevicType", tags = "设备类型管理")
public class SysDeviceTypeController {

    private SysDeviceTypeService sysDeviceTypeService;

    private SysDictDataService sysDictDataService;

    /**
     * 列表
     */
    @ApiOperation(value = "设备类型管理")
    @GetMapping("/list")
    public DdResult getList(Integer classifyId) {
        if (classifyId == null) {
            return DdResult.fail("classifyId不能为空");
        }
        List<SysDeviceType> typeList = sysDeviceTypeService.list(classifyId);
        List<SysDeviceTypeModel> model = new ArrayList<>();
        for (int j = 0; j < typeList.size(); j++) {
            SysDeviceTypeModel sdtm = new SysDeviceTypeModel();
            sdtm.setClassifyId(typeList.get(j).getClassifyId());
            sdtm.setTypeId(typeList.get(j).getTypeId());
            sdtm.setTypeName(typeList.get(j).getTypeName());
            sdtm.setStatus(typeList.get(j).getStatus());
            List<SysDictData> dataList = sysDictDataService.list(typeList.get(j).getTypeId());
            sdtm.setList(dataList);
            model.add(sdtm);
        }

        return DdResult.ok(model);
    }


}
