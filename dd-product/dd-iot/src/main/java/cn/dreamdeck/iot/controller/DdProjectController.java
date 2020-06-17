package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.common.util.AuthContextHolder;
import cn.dreamdeck.iot.service.DdProjectService;
import cn.dreamdeck.iot.service.DdProjectTeamService;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.model.iot.vo.DdProjectVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */
@Api("项目数据")
@RestController
@RequestMapping("/iot/project")
public class DdProjectController {


    @Autowired
    private DdProjectService ddProjectService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DdProjectTeamService projectTeamService;


    //查询项目
    @ApiOperation(value = "根据用户id，查询项目数据（分页，查询）")
    @PostMapping("/getProjectPageVo")
    public DdResult getProjectPageVo(String current, String limit, DdProjectVo ddProjectVo, HttpServletRequest request) {


        String userId = AuthContextHolder.getUserId(request);

        userId = "26";

        if (userId == null) {
            return DdResult.fail("登录信息失效，请从新登录");
        }
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

        QueryWrapper<DdProject> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(ddProjectVo.getProjectName())) {
            wrapper.like("project_name", ddProjectVo.getProjectName());
        }

        if (!StringUtils.isEmpty(ddProjectVo.getProjectType())) {
            wrapper.like("project_type", ddProjectVo.getProjectType());
        }

        if (!StringUtils.isEmpty(ddProjectVo.getStatus())) {
            wrapper.like("status", ddProjectVo.getStatus());
        }

        if (!StringUtils.isEmpty(ddProjectVo.getProjectSite())) {
            wrapper.like("project_site", ddProjectVo.getProjectSite());
        }

        List<Integer> projectByUserId = projectTeamService.getProjectByUserId(userId);
        Integer integer = projectByUserId.get(0);

        //是管理员权限
        if (integer == -1) {
            wrapper.orderByDesc("create_time");
            IPage<DdProject> ddProjectIPage = ddProjectService.page(page, wrapper);
            return DdResult.ok(page);
        }
        List<DdProject> ddProjects = (List<DdProject>) ddProjectService.listByIds(projectByUserId);
        wrapper.orderByDesc("create_time");
        IPage<DdProject> ddProjectIPage = ddProjectService.page(page, wrapper);
        ddProjectIPage.setRecords(ddProjects);
        ddProjectIPage.setTotal(ddProjects.size());
        return DdResult.ok(page);
    }

    //查询项目
    @ApiOperation(value = "根据项目Id获取项目属性")
    @GetMapping("/getProjectById/{projectId}")
    public DdResult getProjectById(@PathVariable(value = "projectId", required = true) String projectId) {
        DdProject ddProject = ddProjectService.getById(projectId);
        return DdResult.ok(ddProject);
    }

    //更改项目
    @ApiOperation(value = "更改项目描述与地址")
    @PostMapping("/updateProject")
    public DdResult updateProject(@PathVariable("projectId") String projectId, @RequestBody DdProjectVo ddProjectVo) {

        DdProject ddProject = ddProjectService.getById(projectId);


        String projectSite = ddProjectVo.getProjectSite();

        String projectDesc = ddProjectVo.getProjectDesc();

        if (!StringUtils.isEmpty(projectSite)) {
            ddProject.setProjectSite(projectSite);
        }

        if (!StringUtils.isEmpty(projectDesc)) {
            ddProject.setProjectDesc(projectDesc);
        }


        boolean b = ddProjectService.updateById(ddProject);
        if (b) {
            return DdResult.ok("更新成功");
        }
        return DdResult.ok("更新失败");
    }


//    //项目
//
//    @GetMapping("/setAll")
//    public DdResult setAll() {
//        String jsonStrBody = "{\"code\":0,\"msg\":\"null\",\"records\":[{\"itemId\":51,\"userId\":59,\"userName\":\"倪照玉\",\"name\":\"AI管家-社区综合导览可视化\",\"alias\":\"AI管家- 社区综合导览可视化\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":92,\"userId\":59,\"userName\":\"倪照玉\",\"name\":\"DD-AI科普认知\",\"alias\":\"DD-AI科普认知产品\",\"type\":\"2\",\"startDate\":null,\"endDate\":\"2020-07-30\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":66,\"userId\":56,\"userName\":\"张楠\",\"name\":\"\",\"alias\":\"DD-北京-病虫害智能监测虫情测报灯产品\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":130,\"userId\":70,\"userName\":\"杨奥茹\",\"name\":\"xxxxxxx\",\"alias\":\"DD-产品-AI科普音乐\",\"type\":\"2\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":135,\"userId\":93,\"userName\":\"乔泽晨\",\"name\":\"DD-产品-AI虚拟环球骑行装置\",\"alias\":\"DD-产品-AI虚拟环球骑行装置\",\"type\":\"2\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":94,\"userId\":61,\"userName\":\"唐达维\",\"name\":null,\"alias\":\"DD-产品-AI虚拟马拉松\",\"type\":\"2\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":62,\"userId\":61,\"userName\":\"唐达维\",\"name\":\"\",\"alias\":\"DD-产品-AR互动app\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"2020-02-25\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":121,\"userId\":82,\"userName\":\"吴飞\",\"name\":\"DD-产品-跳跳泉\",\"alias\":\"DD-产品-跳跳泉\",\"type\":\"2\",\"startDate\":null,\"endDate\":\"2020-07-31\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":132,\"userId\":75,\"userName\":\"曹江生\",\"name\":\"DD-产品-AR互动\",\"alias\":\"DD-产品-虚拟体能大赛\",\"type\":\"2\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"\"},{\"itemId\":72,\"userId\":61,\"userName\":\"唐达维\",\"name\":\"DD-互动灯柱二代\",\"alias\":\"DD-互动灯柱二代\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":69,\"userId\":54,\"userName\":\"高世敏\",\"name\":\" 删除\",\"alias\":\"DD-互动自行车\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":49,\"userId\":10,\"userName\":\"陈铮\",\"name\":\"甲板智慧车间库存采购\",\"alias\":\"DD-库存采购\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"2021-05-29\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":67,\"userId\":51,\"userName\":\"马志伟\",\"name\":\"\",\"alias\":\"DD-跑道灯二代 删除\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":144,\"userId\":70,\"userName\":\"杨奥茹\",\"name\":null,\"alias\":\"DD-趣味声音互动装置\",\"type\":\"2\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":131,\"userId\":70,\"userName\":\"杨奥茹\",\"name\":\"DD-趣味手势互动装置\",\"alias\":\"DD-趣味手势互动装置\",\"type\":\"2\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":57,\"userId\":59,\"userName\":\"倪照玉\",\"name\":\"\",\"alias\":\"DD-软件-信息发布场景产品\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"2020-03-07\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":55,\"userId\":13,\"userName\":\"王美娜\",\"name\":\"智慧安防\",\"alias\":\"DD-软件-智慧安防场景产品\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"2020-06-30\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":95,\"userId\":59,\"userName\":\"倪照玉\",\"name\":\"沈阳金科项目\",\"alias\":\"DD-软件-智慧保洁产品--删除\",\"type\":\"2\",\"startDate\":null,\"endDate\":\"2020-04-20\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":97,\"userId\":59,\"userName\":\"倪照玉\",\"name\":null,\"alias\":\"DD-软件-智慧保洁场景产品\",\"type\":\"2\",\"startDate\":null,\"endDate\":\"2020-04-10\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":53,\"userId\":59,\"userName\":\"倪照玉\",\"name\":\"\",\"alias\":\"DD-软件-智慧灌溉场景产品\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"2020-04-10\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":70,\"userId\":54,\"userName\":\"高世敏\",\"name\":\"\",\"alias\":\"DD-声音互动装置一代 删除\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":113,\"userId\":59,\"userName\":\"倪照玉\",\"name\":\"甲板-北京-甲板APP整合平台\",\"alias\":\"DD-项目交付管理平台\",\"type\":\"2\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":87,\"userId\":70,\"userName\":\"杨奥茹\",\"name\":\"夜光跑道\",\"alias\":\"DD-夜光跑道\",\"type\":\"2\",\"startDate\":\"2020-01-08\",\"endDate\":\"2020-08-30\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":74,\"userId\":82,\"userName\":\"吴飞\",\"name\":\"\",\"alias\":\"DD-智慧路灯二代\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":27,\"userId\":1,\"userName\":\"栗学魁\",\"name\":\"DDMS\",\"alias\":\"DDMS-北京-甲板管理平台升级改造\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"2020-04-30\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":71,\"userId\":53,\"userName\":\"李维亮\",\"name\":\"\",\"alias\":\"DD手势触摸柱二代-删除 删除删除\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":60,\"userId\":49,\"userName\":\"彭亚亚\",\"name\":\"\",\"alias\":\"GIS遥感智能分析产品\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":134,\"userId\":43,\"userName\":\"刘潇\",\"name\":\"中关村大街改造\",\"alias\":\"北京-中关村大街改造\",\"type\":\"1\",\"startDate\":null,\"endDate\":\"2020-12-31\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":52,\"userId\":14,\"userName\":\"徐金波\",\"name\":\"北京甲板智慧科技有限公司-北京-物联网平台\",\"alias\":\"北京甲板智慧科技有限公司-北京-物联网平台\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"2020-06-16\",\"status\":\"9\",\"check\":\"1\"},{\"itemId\":80,\"userId\":29,\"userName\":\"吴菲\",\"name\":\"北京林业大学精准灌溉系统及智慧互动装置系统采购02包\",\"alias\":\"北京林业大学-北京-北林互动景观\",\"type\":\"1\",\"startDate\":\"2019-12-18\",\"endDate\":\"2021-02-01\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":5,\"userId\":4,\"userName\":\"杜国庆\",\"name\":\"风景园林参数化交互式设计平台软件开发\",\"alias\":\"北京林业大学-北京-风景园林参数化交互式设计平台\",\"type\":\"1\",\"startDate\":\"2018-12-03\",\"endDate\":\"2019-02-28\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":117,\"userId\":88,\"userName\":\"窦田\",\"name\":null,\"alias\":\"北控-江门-展园项目\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":42,\"userId\":15,\"userName\":\"李翔\",\"name\":\"城市绿心园林绿化建设工程一标段(设计)施工03、04标段\",\"alias\":\"北林地景-北京-城市绿心智慧系统\",\"type\":\"1\",\"startDate\":\"2019-07-03\",\"endDate\":\"2019-10-04\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":115,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"地景小青山\",\"alias\":\"北林地景-北京-小青山\",\"type\":\"1\",\"startDate\":null,\"endDate\":\"2020-04-22\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":111,\"userId\":69,\"userName\":\"张晋\",\"name\":\"北林地景-河南-登封智慧街道\",\"alias\":\"北林地景-河南-登封智慧街道\",\"type\":\"1\",\"startDate\":null,\"endDate\":\"2021-01-20\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":145,\"userId\":29,\"userName\":\"吴菲\",\"name\":null,\"alias\":\"北林地景-郑州-雕塑公园设计\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":14,\"userId\":15,\"userName\":\"李翔\",\"name\":\"郑州市贾鲁河综合治理西流湖段（中原路-化工路）/ 高新区段（化工路-科学大道）设计-智慧系统设计\",\"alias\":\"北林地景-郑州-贾鲁河智慧系统设计\",\"type\":\"1\",\"startDate\":\"2018-11-19\",\"endDate\":\"\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":59,\"userId\":56,\"userName\":\"张楠\",\"name\":\"\",\"alias\":\"病虫害智能监测识别产品\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":12,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"朝阳公园智慧系统方案设计\",\"alias\":\"朝阳公园-北京-朝阳公园智慧方案\",\"type\":\"1\",\"startDate\":\"2018-11-19\",\"endDate\":\"2020-10-31\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":137,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"创新景观-西单体育公园\",\"alias\":\"创新景观-西单体育公园\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":84,\"userId\":10,\"userName\":\"陈铮\",\"name\":\"能慧万家压力发电地板采购与安装\",\"alias\":\"电科院-浙江丽水-集电地板采购\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":83,\"userId\":10,\"userName\":\"陈铮\",\"name\":\"AI上地公园\",\"alias\":\"电信-北京-上地AI公园\",\"type\":\"1\",\"startDate\":\"2019-12-25\",\"endDate\":\"2020-12-25\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":13,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"通州区智慧园林系统设计\",\"alias\":\"电信-北京-通州区智慧园林系统设计\",\"type\":\"1\",\"startDate\":\"2018-11-19\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":48,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"通州智慧园林系统平台二期\",\"alias\":\"电信-北京-通州智慧园林平台二期\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":44,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"东郊森林公园智慧灌溉示范区\",\"alias\":\"东郊森林公园-北京-智慧灌溉\",\"type\":\"1\",\"startDate\":\"2019-07-01\",\"endDate\":\"2019-10-31\",\"status\":\"6\",\"check\":\"1\"},{\"itemId\":26,\"userId\":4,\"userName\":\"杜国庆\",\"name\":\"福州大学城市规划设计虚拟技术支持应用系统\",\"alias\":\"福州大学-福州-城市规划设计虚拟技术支持应用系统\",\"type\":\"1\",\"startDate\":\"2018-12-01\",\"endDate\":\"2018-12-02\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":32,\"userId\":59,\"userName\":\"倪照玉\",\"name\":\"梦想甲板硬件产品册\",\"alias\":\"公司宣传册\",\"type\":\"1\",\"startDate\":\"2019-02-18\",\"endDate\":\"2019-03-31\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":16,\"userId\":9,\"userName\":\"李长霖\",\"name\":\"海淀公园智慧系统策划方案&AR游戏策划方案\",\"alias\":\"海淀公园-北京-智慧系统策划\",\"type\":\"1\",\"startDate\":\"2018-12-10\",\"endDate\":\"2018-12-10\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":33,\"userId\":9,\"userName\":\"李长霖\",\"name\":\"北京大学高等技术研究院展厅内容制作\",\"alias\":\"杭州北大展厅\",\"type\":\"1\",\"startDate\":\"2019-03-20\",\"endDate\":\"2019-05-01\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":10,\"userId\":11,\"userName\":\"王铮\",\"name\":\"红旗CES展全景广告宣传页开发合同\",\"alias\":\"红旗-北京-CES展全景广告宣传页\",\"type\":\"1\",\"startDate\":\"2018-12-07\",\"endDate\":\"2019-01-09\",\"status\":\"6\",\"check\":\"1\"},{\"itemId\":76,\"userId\":11,\"userName\":\"王铮\",\"name\":\"广州车展红旗全景网站\",\"alias\":\"红旗-广州-车展全景网站\",\"type\":\"1\",\"startDate\":\"2019-11-08\",\"endDate\":\"2019-12-30\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":112,\"userId\":61,\"userName\":\"唐达维\",\"name\":\"AI互动骑行装置\",\"alias\":\"互动自行车-杭州-绿城建研院\",\"type\":\"2\",\"startDate\":null,\"endDate\":\"2020-06-01\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":30,\"userId\":10,\"userName\":\"陈铮\",\"name\":\"海淀区世园会花坛项目\",\"alias\":\"花木公司-北京-世园会海淀展园花坛项目\",\"type\":\"1\",\"startDate\":\"2019-02-20\",\"endDate\":\"2019-04-30\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":65,\"userId\":43,\"userName\":\"刘潇\",\"name\":\"\",\"alias\":\"环境感知可视化标识\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":58,\"userId\":56,\"userName\":\"张楠\",\"name\":\"\",\"alias\":\"环境感知模块场景产品\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":120,\"userId\":29,\"userName\":\"吴菲\",\"name\":\"中新天津生态城起步区05-07-01-03池块（四期）室外园林专业分包工程合同\",\"alias\":\"吉宝置业-天津-AR体能互动\",\"type\":\"1\",\"startDate\":null,\"endDate\":\"2020-06-30\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":139,\"userId\":29,\"userName\":\"吴菲\",\"name\":null,\"alias\":\"金地-北京-金盏互动景观\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":29,\"userId\":56,\"userName\":\"张楠\",\"name\":\"金控数据-成都智慧水务\",\"alias\":\"金控数据-成都智慧水\",\"type\":\"1\",\"startDate\":\"2019-01-02\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":20,\"userId\":56,\"userName\":\"张楠\",\"name\":\"襄阳污水厂大屏系统\",\"alias\":\"金控数据-襄阳-水务智慧管理平台\",\"type\":\"1\",\"startDate\":\"2018-11-19\",\"endDate\":\"2018-11-19\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":110,\"userId\":10,\"userName\":\"陈铮\",\"name\":\"立错了待删除\",\"alias\":\"立错了待删除的-立错了待删除\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"11\",\"check\":\"1\"},{\"itemId\":47,\"userId\":29,\"userName\":\"吴菲\",\"name\":\"北京房山良乡大学城中央绿化带智慧系统设计\",\"alias\":\"良乡大学城-北京-良乡大学城中央绿化带智慧系统设计\",\"type\":\"1\",\"startDate\":\"2019-08-14\",\"endDate\":\"2021-10-01\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":103,\"userId\":69,\"userName\":\"张晋\",\"name\":\"龙湖白辛庄示范区\",\"alias\":\"龙湖-北京-白辛庄示范区智慧景观\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":41,\"userId\":59,\"userName\":\"倪照玉\",\"name\":\"\",\"alias\":\"龙湖-北京-海淀科技公园二期\",\"type\":\"1\",\"startDate\":\"2019-06-21\",\"endDate\":\"2019-06-21\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":3,\"userId\":14,\"userName\":\"徐金波\",\"name\":\"龙湖中关村环保科技园分部分项工程 集电地板、中控系统、组网方案施工工程\",\"alias\":\"龙湖-北京-海淀科技公园中控系统\",\"type\":\"1\",\"startDate\":\"2018-08-20\",\"endDate\":\"2018-11-20\",\"status\":\"15\",\"check\":\"1\"},{\"itemId\":19,\"userId\":9,\"userName\":\"李长霖\",\"name\":\"龙湖高碑店列车新城中控系统\",\"alias\":\"龙湖-高碑店-中控系统项目\",\"type\":\"1\",\"startDate\":\"2018-11-20\",\"endDate\":\"2019-03-01\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":119,\"userId\":71,\"userName\":\"傅饶\",\"name\":null,\"alias\":\"龙湖-广州-首开龙湖天奕项目\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":122,\"userId\":56,\"userName\":\"张楠\",\"name\":null,\"alias\":\"龙湖-海淀-龙湖能量公园二期项目\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":124,\"userId\":59,\"userName\":\"倪照玉\",\"name\":null,\"alias\":\"龙湖-唐山-项目\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":37,\"userId\":41,\"userName\":\"杨磊\",\"name\":\"绿地天津自贸港示范区智慧园区\",\"alias\":\"绿地-天津-自贸港智慧园\",\"type\":\"1\",\"startDate\":\"2019-06-06\",\"endDate\":\"2019-07-31\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":129,\"userId\":71,\"userName\":\"傅饶\",\"name\":null,\"alias\":\"牟平大数据服务中心-烟台市牟平区-智慧社区试点项目\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":77,\"userId\":11,\"userName\":\"王铮\",\"name\":\"首钢项目集电地板楼梯-地砖采购与安装\",\"alias\":\"平创建建设-北京-首钢集电楼梯\",\"type\":\"1\",\"startDate\":\"2019-11-27\",\"endDate\":\"2020-01-05\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":82,\"userId\":9,\"userName\":\"李长霖\",\"name\":\"风景园林工程管理虚拟仿真软件采购\",\"alias\":\"清华大学-北京-虚拟仿真教学平台\",\"type\":\"1\",\"startDate\":\"2019-12-24\",\"endDate\":\"2020-12-01\",\"status\":\"6\",\"check\":\"1\"},{\"itemId\":25,\"userId\":59,\"userName\":\"倪照玉\",\"name\":\"天津大通关智慧园区电子沙盘\",\"alias\":\"神州数码-天津-大通关智慧园区电子沙盘\",\"type\":\"1\",\"startDate\":\"2018-11-27\",\"endDate\":\"2019-01-01\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":11,\"userId\":11,\"userName\":\"王铮\",\"name\":\"首钢秦皇岛智慧园区智慧化设计\",\"alias\":\"首钢-秦皇岛-智慧园区智慧化设计\",\"type\":\"1\",\"startDate\":\"2018-10-13\",\"endDate\":\"2018-11-30\",\"status\":\"9\",\"check\":\"1\"},{\"itemId\":50,\"userId\":10,\"userName\":\"陈铮\",\"name\":\"金融街爆闪灯灯控项目\",\"alias\":\"数知科技-北京-金融街爆闪灯\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":46,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"数知科技西城区金融街智慧跑道\",\"alias\":\"数知科技-北京-金融街智慧跑道\",\"type\":\"1\",\"startDate\":\"2019-08-10\",\"endDate\":\"2019-09-20\",\"status\":\"12\",\"check\":\"1\"},{\"itemId\":100,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"金融街智慧跑道软件接口开发\",\"alias\":\"数知科技-北京-金融街智慧跑道软件接口开发\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":61,\"userId\":1,\"userName\":\"栗学魁\",\"name\":\"\",\"alias\":\"水厂工艺模拟产品\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":79,\"userId\":29,\"userName\":\"吴菲\",\"name\":\"天安云谷产业园二期极创公园智慧化互动设备 供货及安装工程合同\",\"alias\":\"天安-深圳-极创公园\",\"type\":\"1\",\"startDate\":\"2019-12-01\",\"endDate\":\"2020-02-01\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":34,\"userId\":1,\"userName\":\"栗学魁\",\"name\":\"天津生态城网站效果提升\",\"alias\":\"天津生态城网站效果提升\",\"type\":\"1\",\"startDate\":\"2019-03-26\",\"endDate\":\"2019-04-30\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":75,\"userId\":9,\"userName\":\"李长霖\",\"name\":\"成都双流五湖四海互动设施\",\"alias\":\"铁汉生态-成都-双流五湖四海互动设施\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":15,\"userId\":15,\"userName\":\"李翔\",\"name\":\"双流区空港中央公园之五湖四海一期工程勘察-设计-施工总承包（EPC）-智慧系统设计\",\"alias\":\"铁汉生态-成都-双流五湖四海智慧系统设计\",\"type\":\"1\",\"startDate\":\"2018-11-19\",\"endDate\":\"\",\"status\":\"12\",\"check\":\"1\"},{\"itemId\":91,\"userId\":10,\"userName\":\"陈铮\",\"name\":\"亮马河互动设备\",\"alias\":\"同方-亮马河-互动设备\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"11\",\"check\":\"1\"},{\"itemId\":2,\"userId\":10,\"userName\":\"陈铮\",\"name\":\"亮马河可视化中控系统\",\"alias\":\"同方-亮马河-中控系统\",\"type\":\"1\",\"startDate\":\"2018-12-03\",\"endDate\":\"2019-12-01\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":98,\"userId\":29,\"userName\":\"吴菲\",\"name\":\"时代中心望京提升项目智能化工程合同\",\"alias\":\"万科-北京-望京安联文创智能化设施\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"1\",\"check\":\"1\"},{\"itemId\":109,\"userId\":56,\"userName\":\"张楠\",\"name\":\"万科建研跑道智能化部品采购合同2\",\"alias\":\"万科-东莞-打卡杆一体机传感器路灯采购\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"1\",\"check\":\"1\"},{\"itemId\":108,\"userId\":56,\"userName\":\"张楠\",\"name\":\"万科建研跑道智能化部品采购合同1\",\"alias\":\"万科-东莞-跳泉及地板采购\",\"type\":\"1\",\"startDate\":null,\"endDate\":\"2020-05-20\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":38,\"userId\":11,\"userName\":\"王铮\",\"name\":\"杭州万科天空之镜\",\"alias\":\"万科-杭州-天空之城\",\"type\":\"1\",\"startDate\":\"2019-06-12\",\"endDate\":\"2019-09-30\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":9,\"userId\":11,\"userName\":\"王铮\",\"name\":\"吉林滨江九里项目电子区位沙盘合同\",\"alias\":\"万科-吉林-电子区位沙盘\",\"type\":\"1\",\"startDate\":\"2018-05-31\",\"endDate\":\"2018-07-31\",\"status\":\"6\",\"check\":\"1\"},{\"itemId\":140,\"userId\":29,\"userName\":\"吴菲\",\"name\":null,\"alias\":\"万科-廊坊-安次项目互动景观\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":7,\"userId\":11,\"userName\":\"王铮\",\"name\":\"石家庄万科南高营项目示范区互动投影合同\",\"alias\":\"万科-石家庄-互动投影\",\"type\":\"1\",\"startDate\":\"2018-11-16\",\"endDate\":\"2018-12-30\",\"status\":\"6\",\"check\":\"1\"},{\"itemId\":18,\"userId\":9,\"userName\":\"李长霖\",\"name\":\"万科石家庄区位沙盘\",\"alias\":\"万科-石家庄-区位沙盘\",\"type\":\"1\",\"startDate\":\"2018-12-03\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":8,\"userId\":11,\"userName\":\"王铮\",\"name\":\"天津万科滨海项目万科馆展览展示设计合同\",\"alias\":\"万科-天津-大都会展览展示设计\",\"type\":\"1\",\"startDate\":\"2017-08-30\",\"endDate\":\"2017-11-01\",\"status\":\"12\",\"check\":\"1\"},{\"itemId\":101,\"userId\":11,\"userName\":\"王铮\",\"name\":\"天津万科西华府多媒体动画制作\",\"alias\":\"万科-天津-多媒体制作\",\"type\":\"1\",\"startDate\":null,\"endDate\":\"2019-08-18\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":88,\"userId\":29,\"userName\":\"吴菲\",\"name\":\"万科雄安项目未来树合同书\",\"alias\":\"万科-雄安-未来树\",\"type\":\"1\",\"startDate\":\"2020-01-14\",\"endDate\":\"2021-01-14\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":127,\"userId\":9,\"userName\":\"李长霖\",\"name\":\"未来科学城-北京-温榆河-智慧公园\",\"alias\":\"未来科学城-北京-温榆河-智慧公园\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":28,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"未来科学城综合展示系统\",\"alias\":\"未来科学城-北京-智慧展示系统\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":138,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"未来科学城-温榆河昌平段一期\",\"alias\":\"未来科学城-温榆河昌平段一期\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":125,\"userId\":11,\"userName\":\"王铮\",\"name\":\"乌镇未来科技体验园\",\"alias\":\"乌镇人民公园-中景艺-乌镇\",\"type\":\"1\",\"startDate\":null,\"endDate\":\"2020-09-30\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":78,\"userId\":29,\"userName\":\"吴菲\",\"name\":\"科学城智慧公园智慧设施施工图设计\",\"alias\":\"新地中联-深圳-科学城智慧公园智慧设施施工图设计\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":63,\"userId\":61,\"userName\":\"唐达维\",\"name\":\"\",\"alias\":\"行为分析+U3D互动程序\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":142,\"userId\":29,\"userName\":\"吴菲\",\"name\":null,\"alias\":\"雄安生态公司-雄安-绿博会智能化设计\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":136,\"userId\":43,\"userName\":\"刘潇\",\"name\":\"旭辉新都智能化设施\",\"alias\":\"旭辉-成都-新都综合体智能化\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":116,\"userId\":11,\"userName\":\"王铮\",\"name\":\"全球AI艺术大赛Gaac奖金发放协议\",\"alias\":\"亿评-无-奖金发放\",\"type\":\"1\",\"startDate\":null,\"endDate\":\"2020-04-28\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":126,\"userId\":62,\"userName\":\"于萌\",\"name\":\"亮马河效果图\",\"alias\":\"易景道-北京-效果图\",\"type\":\"1\",\"startDate\":null,\"endDate\":\"2020-06-01\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":123,\"userId\":11,\"userName\":\"王铮\",\"name\":\"factory found 买手店地砖采购\",\"alias\":\"英皇地砖-江苏七彩虹-北京\",\"type\":\"1\",\"startDate\":null,\"endDate\":\"2020-07-01\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":6,\"userId\":11,\"userName\":\"王铮\",\"name\":\" \",\"alias\":\"优贯-北京-金魔方首饰APP\",\"type\":\"1\",\"startDate\":\"2018-04-01\",\"endDate\":\"2018-12-30\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":141,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"正和-唐山园博会\",\"alias\":\"正和-唐山园博会\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":4,\"userId\":10,\"userName\":\"陈铮\",\"name\":\"永宁路北人工湿地智能监测交互展示系统\",\"alias\":\"正和恒基-长春-伊通河交互展示系统\",\"type\":\"1\",\"startDate\":\"2018-11-20\",\"endDate\":\"2019-01-01\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":56,\"userId\":13,\"userName\":\"王美娜\",\"name\":\"\",\"alias\":\"智慧交通场景产品\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":54,\"userId\":59,\"userName\":\"倪照玉\",\"name\":\"\",\"alias\":\"智慧照明场景产品\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":64,\"userId\":61,\"userName\":\"唐达维\",\"name\":\"\",\"alias\":\"智能配电柜\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":73,\"userId\":11,\"userName\":\"王铮\",\"name\":\"\",\"alias\":\"智能语音垃圾桶\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":68,\"userId\":53,\"userName\":\"李维亮\",\"name\":\"\",\"alias\":\"智能座椅\",\"type\":\"2\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":43,\"userId\":44,\"userName\":\"董康龙\",\"name\":\"中海大兴瀛海互动景观项目\",\"alias\":\"中海-大兴-瀛海互动景观\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":143,\"userId\":88,\"userName\":\"窦田\",\"name\":\"中建-四川仁寿-城市会客厅\",\"alias\":\"中建-四川仁寿-城市会客厅\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":17,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"中建生态环境智慧运营管控平台建设\",\"alias\":\"中建水务-北京-中建水务运营平台\",\"type\":\"1\",\"startDate\":\"2018-12-10\",\"endDate\":\"2018-12-10\",\"status\":\"13\",\"check\":\"1\"},{\"itemId\":45,\"userId\":11,\"userName\":\"王铮\",\"name\":\"乌镇未来科技体验园智慧化提升\",\"alias\":\"中景艺-乌镇-乌镇未来科技体验园\",\"type\":\"1\",\"startDate\":\"2019-07-31\",\"endDate\":\"2020-09-30\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":31,\"userId\":4,\"userName\":\"杜国庆\",\"name\":\"武汉人才大数据可视化项目\",\"alias\":\"中科梧桐-武汉-人才大数据\",\"type\":\"1\",\"startDate\":\"2019-02-22\",\"endDate\":\"2019-06-22\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":36,\"userId\":9,\"userName\":\"李长霖\",\"name\":\"中煤-可视化\",\"alias\":\"中煤-可视化\",\"type\":\"1\",\"startDate\":\"2019-04-02\",\"endDate\":\"2019-07-01\",\"status\":\"0\",\"check\":\"1\"},{\"itemId\":40,\"userId\":4,\"userName\":\"杜国庆\",\"name\":\"智能泵站三维综合智能一体化平台系统原型开发\",\"alias\":\"中水三立-合肥-智能泵站\",\"type\":\"1\",\"startDate\":\"2019-06-19\",\"endDate\":\"2019-12-01\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":81,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"中通服温榆河AR科普APP\",\"alias\":\"中通服-温榆河-AR科普APP\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":90,\"userId\":12,\"userName\":\"伍晓苑\",\"name\":\"温榆河湿地画卷\",\"alias\":\"中通服-温榆河-湿地画卷\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"11\",\"check\":\"1\"},{\"itemId\":39,\"userId\":11,\"userName\":\"王铮\",\"name\":\"中冶ai兴隆智慧社区\",\"alias\":\"中冶-兴隆-ai智慧社区\",\"type\":\"1\",\"startDate\":\"2019-05-01\",\"endDate\":\"2019-09-30\",\"status\":\"12\",\"check\":\"1\"},{\"itemId\":21,\"userId\":9,\"userName\":\"李长霖\",\"name\":\"淄博交互琴弦\",\"alias\":\"淄博-交互琴弦\",\"type\":\"1\",\"startDate\":\"2018-09-06\",\"endDate\":\"2018-12-11\",\"status\":\"11\",\"check\":\"1\"},{\"itemId\":102,\"userId\":73,\"userName\":\"张皓\",\"name\":\"淄博荣耀公园互动喷泉\",\"alias\":\"淄博-荣耀公园互动喷泉\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"0\",\"check\":\"1\"},{\"itemId\":35,\"userId\":34,\"userName\":\"王运峰\",\"name\":\"淄博猪龙河景观智慧系统\",\"alias\":\"淄博-猪龙河景观智慧系统\",\"type\":\"1\",\"startDate\":\"2019-04-02\",\"endDate\":\"2020-04-01\",\"status\":\"1\",\"check\":\"1\"},{\"itemId\":93,\"userId\":41,\"userName\":\"杨磊\",\"name\":\"邢台触摸灯柱\",\"alias\":\"自由创林-邢台-触摸灯柱\",\"type\":\"1\",\"startDate\":null,\"endDate\":null,\"status\":\"11\",\"check\":\"1\"},{\"itemId\":89,\"userId\":9,\"userName\":\"李长霖\",\"name\":\"北京朝阳左家庄智慧社区-智慧防疫\",\"alias\":\"左家庄街道-北京-智慧防疫\",\"type\":\"1\",\"startDate\":\"\",\"endDate\":\"\",\"status\":\"1\",\"check\":\"1\"}]}";
//
//        JSONObject jsonObject = JSONObject.parseObject(jsonStrBody);
//        List<Rroject> receiveList = (List<Rroject>) JSONArray.parseArray(jsonObject.getString("records"), Rroject.class);
//
//        System.out.println(receiveList);
//        for (Rroject rroject : receiveList) {
//
//            DdProject ddProject = new DdProject();
//            ddProject.setProjectName(rroject.getAlias());
//            ddProject.setProjectType(rroject.getType());
//            ddProject.setProjectDesc(rroject.getAlias());
//            ddProject.setStatus(rroject.getStatus());
//            ddProject.setProjectImg("http://file.dreamdeck.cn/app/icons//website/logo/2019-05/23/5d3295c4-a8d3-4779-b614-b1a7dac0db09.gif");
//            ddProject.setProjectSite("项目地址");
//            ddProject.setUserId(rroject.getUserId());
//            ddProject.setUserName(rroject.getUserName());
//            ddProject.setCreateTime(DateUtil.getTime());
//            ddProjectService.save(ddProject);
//        }
//
//        return DdResult.ok("执行成功");
//    }


}

