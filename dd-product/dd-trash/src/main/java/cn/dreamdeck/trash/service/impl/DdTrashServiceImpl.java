package cn.dreamdeck.trash.service.impl;

import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.data.Util;
import cn.dreamdeck.common.execption.DdException;
import cn.dreamdeck.iot.client.DdIotFeignService;
import cn.dreamdeck.model.iot.DeviceConfig.DeviceConfig;
import cn.dreamdeck.model.iot.device.innovate.DdTrashConfig;
import cn.dreamdeck.model.trash.DdTrash;
import cn.dreamdeck.service.constant.RedisConst;
import cn.dreamdeck.trash.mapper.DdTrashMapper;
import cn.dreamdeck.trash.netty.DdServerHandler;
import cn.dreamdeck.trash.service.DdTrashService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lxn
 * @since 2020-06-04
 */
@Service
public class DdTrashServiceImpl extends ServiceImpl<DdTrashMapper, DdTrash> implements DdTrashService {
    private static final Logger logger = LoggerFactory.getLogger(DdTrashServiceImpl.class);


//    @Autowired
//    private StringRedisTemplate redisTemplate;

    @Autowired
    private DdIotFeignService ddIotFeignService;

    @Autowired
    private RedisTemplate redisTemplate;

    //开桶
    @Override
    public String openTrash(String trashId, String num) {

        DdTrash ddTrash = baseMapper.selectOne(new QueryWrapper<DdTrash>().eq("device_id", trashId));
        String data = openData(ddTrash.getSoleId(), Integer.valueOf(num));
        byte[] bytes = Util.hexStrToBinaryStr(data);
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        ChannelHandlerContext ctx = null;
        try {
            ctx = DdServerHandler.map.get(ddTrash.getSoleId());
        } catch (Exception e) {
            if (ctx == null) {
                ddTrash.setStatus(1);
                baseMapper.updateById(ddTrash);
                return "设备已离线";
            }

        }


        ctx.channel().writeAndFlush(buf);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "设备已打开" + num + "号桶";
    }


    //状态
    @Override
    public String status(String trashId) {
        DdTrash ddTrash = baseMapper.selectOne(new QueryWrapper<DdTrash>().eq("device_id", trashId));

        return ddTrash.getStatus().toString();
    }

    //添加到设备列表
    @Override
    public boolean saveDevice(DdTrash ddTrash) {

        Integer ddTrashId = null;
        DdTrash ddTrash1 =null;
        //处理id重复
        List<DdTrash> sole_id = baseMapper.selectList(new QueryWrapper<DdTrash>().eq("sole_id", ddTrash.getSoleId()));

        if (sole_id.size() > 1) {
            new DdException("设备Id已存在", 20007);
            return false;
        }
        if (null != sole_id && sole_id.size() != 0) {
             ddTrash1 = sole_id.get(0);
            ddTrashId = ddTrash1.getDeviceId();
            if (null != ddTrash1) {
                ddTrash1.setStatus(0);
                baseMapper.updateById(ddTrash1);
                return true;
            }
        }

        ddTrash.setDeviceName("默认名称");
        ddTrash.setProjectId(0);
        ddTrash.setClassifyId(5);
        ddTrash.setTypeId(504);
        ddTrash.setAuiaModel("科大讯飞模式");
        ddTrash.setLatitude("0");
        ddTrash.setLongitude("0");
        ddTrash.setLastTime(DateUtil.getTime());
        ddTrash.setCreateTime(DateUtil.getTime());
        ddTrash.setBrandId(9);
        ddTrash.setModelId(13);
        baseMapper.insert(ddTrash);


         ddTrash1 = baseMapper.selectOne(new QueryWrapper<DdTrash>().eq("sole_id", ddTrash.getSoleId()));
        ddTrashId = ddTrash1.getDeviceId();


        DeviceConfig deviceConfig = new DeviceConfig();
        deviceConfig.setDeviceName("智能语音垃圾桶");
        deviceConfig.setModelId(13);
        deviceConfig.setProjectId(0);

        deviceConfig.setSoleId(ddTrash.getSoleId());
        DdTrashConfig trash = new DdTrashConfig();

        trash.setAiauModel(ddTrash.getAuiaModel());
        trash.setSoleId(ddTrash.getSoleId());
        trash.setTrashId(ddTrashId);
        deviceConfig.setConfig(trash);
        ddIotFeignService.saveDeviceVo(deviceConfig);
        //设备只发一次注册，不需要处理缓存穿透问题
        return true;
    }

    @Override
    public String openNum(String trashId) {

        Map<String, Integer> entries = redisTemplate.opsForHash().entries(RedisConst.DEVICE_TRASH_OPENNUM + trashId);
        if (entries != null && entries.size() > 0) {
            return entries.toString();
        }
        return "数据异常";
    }


    //更新设备状态
    @Override
    public boolean updateDevice(String soleId) {
        DdTrash ddTrash = baseMapper.selectOne(new QueryWrapper<DdTrash>().eq("sole_id", soleId));
        ddTrash.setStatus(1);
        int i = baseMapper.updateById(ddTrash);
        if (i > 0) {
            return true;
        }
        return false;
    }


    @Override
    public DdTrash selectByauiaModel(String model) {
        DdTrash ddTrash = baseMapper.selectOne(new QueryWrapper<DdTrash>().eq("auia_model", model));
        return ddTrash;
    }

    @Override
    public String satisfaction(String trashId) {
        DdTrash ddTrash = baseMapper.selectOne(new QueryWrapper<DdTrash>().eq("device_id", trashId));
        String num = (String) redisTemplate.opsForValue().get(RedisConst.DEVICE_TRASH_NUM + ddTrash.getSoleId());
        return num;
    }


    /**
     * 开桶数据
     *
     * @param id  设备id
     * @param num 桶号
     * @return
     */
    @Override
    public String openData(int id, int num) {
        byte[] msg = new byte[12];

        msg[0] = (byte) 0x68;
        msg[1] = Util.hexToByte(String.valueOf(id));
        msg[2] = (byte) 0x00;
        msg[3] = (byte) 0x00;
        msg[4] = (byte) 0x00;
        msg[5] = (byte) 0x0E;
        msg[6] = (byte) 0x03;
        msg[7] = (byte) 0x00;
        msg[8] = (byte) 0x00;
        msg[9] = (byte) 0x00;

        msg[10] = Util.hexToByte(String.valueOf(num));


        byte sum = 0;
        for (int i = 0; i < msg.length; i++) {
            sum += Util.getUnsignedByte(msg[i]);
        }
        msg[11] = (byte) ((byte) ~sum + 1);

        String value = Util.BinaryToHexStrings(msg);
        System.out.println(value);
        return value;


    }

}
