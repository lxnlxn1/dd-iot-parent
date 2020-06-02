package cn.dreamdeck.iot.controller;

import cn.dreamdeck.iot.service.SysDictDataService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("sysDictData")
@Api(value = "sysDictData", tags = "字典表")
public class SysDictDataController {

    private SysDictDataService sysDictDataService;


}
