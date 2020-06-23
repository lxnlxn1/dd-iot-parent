package cn.dreamdeck.trash.service.impl;

import cn.dreamdeck.common.data.Util;
import cn.dreamdeck.model.trash.DdTrash;
import cn.dreamdeck.trash.mapper.DdTrashMapper;
import cn.dreamdeck.trash.netty.DdServerHandler;
import cn.dreamdeck.trash.service.DdTrashService;
import cn.dreamdeck.trash.service.RedisService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private RedisService redisService;

    //开桶
    @Override
    public String openTrash(String ip, String num) {

        DdTrash ddTrash = baseMapper.selectOne(new QueryWrapper<DdTrash>().eq("device_ip", ip));
        String data = openData(ddTrash.getSoleId(), Integer.valueOf(num));
        byte[] bytes = Util.hexStrToBinaryStr(data);
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        ChannelHandlerContext ctx = DdServerHandler.map.get(ip);
        if (ctx == null) {
            return "设备已离线";
        }


        DdServerHandler.map.get(ip).channel().writeAndFlush(buf);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return "设备已打开" + num + "号桶";
    }


    //状态
    @Override
    public String status(String ip) {

        String result = DdServerHandler.resuleMap.get(ip);
        return result;
    }

    //添加到设备列表
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDevice(DdTrash ddTrash) {


        //添加品牌
//        trashModel.setModelId();

        //添加设备型号
//
//        //设备只发一次注册，不需要处理缓存穿透问题

//        DdDevice ddDevice = ddDeviceService.selectIpAndlassifyId(trashModel.getIp(), 4);
//
//        if (ddDevice == null) {
//
//            ddDevice = new DdDevice();
//            ddDevice.setDeviceId(idGenerator.createId("ddz"));
//            ddDevice.setProjectId("项目ID");
//            ddDevice.setClassifyId(4);
//            ddDevice.setStatus(trashModel.getIsOnline());
//            ddDevice.setDeviceIp(trashModel.getIp());
//            ddDevice.setDeviceName("智能语音垃圾桶");
//            ddDevice.setLastTime(DateUtil.getTime());
//            ddDevice.setCreateTime(DateUtil.getTime());
//            int save = ddDeviceService.save(ddDevice);
//            logger.info("设备添加进数据库了");
//            if (save == 1) return true;
//
//        } else {
//            if(ddDevice.getStatus()==0){
//                //进行更新
//                ddDevice.setStatus(1);
//                ddDevice.setLastTime(DateUtil.getTime());
//                ddDeviceService.update(ddDevice);
//            }
//            logger.info("设备已经存在");
//
//            return false;
//
//
//        }
//
        return false;

    }

    @Override
    public Map<String, Integer> openNum(String ip) {
        Map<String, Integer> trashNum = redisService.getTrashNum(ip);
        return trashNum;
    }

    @Override
    public boolean updateDevice(String ip) {
//        DdDevice ddDevice = ddDeviceService.selectIpAndlassifyId(ip, 4);
//        if (ddDevice == null) {
//            logger.info("设备不存在或此设备未开启");
//            return false;
//        }
//        if (StringUtils.isEmpty(ddDevice.getStatus())) return false;
//        ddDevice.setStatus(0);
//        ddDevice.setLastTime(DateUtil.getTime());
//        int update = ddDeviceService.update(ddDevice);
//        if (update == 1) return true;
        return false;
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

    @Override
    public DdTrash selectByauiaModel(String model) {
        DdTrash ddTrash = baseMapper.selectOne(new QueryWrapper<DdTrash>().eq("auia_model", model));
        return ddTrash;
    }


}
