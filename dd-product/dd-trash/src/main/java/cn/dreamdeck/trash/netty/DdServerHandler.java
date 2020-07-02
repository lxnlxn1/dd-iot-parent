package cn.dreamdeck.trash.netty;

import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.data.Util;
import cn.dreamdeck.model.trash.DdTrash;
import cn.dreamdeck.service.constant.RedisConst;
import cn.dreamdeck.trash.service.DdTrashService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//物联网 开启检测端口 并写入数据库
@Component
@Sharable
public class DdServerHandler extends ChannelInboundHandlerAdapter {


    //  将当前客户端连接 存入map   实现控制设备下发 参数
    public static Map<Integer, ChannelHandlerContext> map = new ConcurrentHashMap<Integer, ChannelHandlerContext>();

       private Map<ChannelHandlerContext, Integer> clientOvertimeMap = new ConcurrentHashMap<>();


    //返回结果map
    public static Map<Integer, String> resuleMap = new ConcurrentHashMap<Integer, String>();

    private static final Logger logger = LoggerFactory.getLogger(DdServerHandler.class);

    @Autowired
    private RedisTemplate redisTemplate;



    @Autowired
    private DdTrashService trashService;


    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // String url = ctx.channel().remoteAddress().toString();
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = ipSocket.getAddress().getHostAddress();
        // map.put(ip, ctx);
        System.out.println("有新客户端连接接入。。。" + ctx.channel().remoteAddress() + " " + ctx.channel().id());
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = ipSocket.getAddress().getHostAddress();
        ChannelId id1 = ctx.channel().id();

        //处理

        //获取缓存键值
//        Set<String> keys = redisTemplate.keys(RedisConst.DEVICE_TRASH_IP+"*");
//        for (String key : keys) {
//            String ip1 = (String) redisTemplate.opsForValue().get(key);
//            if ((ip+id1.toString()).equals(ip1)) {
//                    String[] split = key.split(":");
//                    String id = split[split.length-1];
//                    trashService.updateDevice(id);
//
//            }
//        }

//        String type = (String) redisTemplate.opsForHash().get(ip, "type");
//        String deviceId = (String) redisTemplate.opsForHash().get(ip, "deviceId");


        System.out.println("失去连接" + ip + id1);
    }

    /**
     * 获取数据
     *
     * @param ctx  上下文
     * @param msgs 获取的数据
     * @throws UnsupportedEncodingException
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msgs) {

        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = ipSocket.getAddress().getHostAddress();
        ChannelId id = ctx.channel().id();

        ByteBuf readMessage = (ByteBuf) msgs;
        String prefixValue = ByteBufUtil.hexDump(readMessage);
        logger.info("接收到的数据" + prefixValue);
        //获取客户端的请求地址  取到的值为客户端的 ip+端口号
        String syncMsg = prefixValue.substring(0, 2);//同步头
        switch (syncMsg) {
            case "68":
                String deviceIdValue = prefixValue.substring(2, 4);//设备ID
                String deviceIdValue1 = prefixValue.substring(4, 6);
                String deviceIdValue2 = prefixValue.substring(6, 8);
                String deviceIdValue3 = prefixValue.substring(8, 10);
                Integer deviceId = Integer.parseInt(deviceIdValue, 16);
                map.put(deviceId, ctx);
                String msgType = prefixValue.substring(10, 12);//消息类型
                //logger.info("垃圾桶>>>" + deviceId + "消息类型" + msgType);
                switch (msgType) {
                    case "00":
                        logger.info("垃圾桶>>>" + deviceId + "注册");
                        byte[] msg = new byte[10];
                        msg[0] = (byte) 0x68;
                        msg[1] = Util.hexToByte(String.valueOf(deviceId));
                        msg[2] = (byte) 0x00;
                        msg[3] = (byte) 0x00;
                        msg[4] = (byte) 0x00;
                        msg[5] = (byte) 0x00;
                        msg[6] = (byte) 0x01;
                        msg[7] = (byte) 0x00;
                        msg[8] = (byte) 0x01;
                        byte sum = 0;
                        for (int i = 0; i < msg.length; i++) {
                            sum += Util.getUnsignedByte(msg[i]);
                        }
                        msg[9] = (byte) ((byte) ~sum + 1);
                        String value = Util.BinaryToHexStrings(msg);
                        byte[] s = null;
                        s = Util.hexStrToBinaryStr(value);
                        ByteBuf message = Unpooled.copiedBuffer(s);
                        ctx.writeAndFlush(message);//返回信息
                        DdTrash ddTrash = new DdTrash();
                        ddTrash.setSoleId(deviceId);
                        ddTrash.setStatus(0);
                        trashService.saveDevice(ddTrash);
                        String ip1 = (String) redisTemplate.opsForValue().get(RedisConst.DEVICE_TRASH_IP + deviceId);
                        if (null == ip1 || !(ip+id.toString()).equals(ip1)) {
                            redisTemplate.opsForValue().set(RedisConst.DEVICE_TRASH_IP + deviceId, ip+id.toString());
                        }

                        logger.info("已发送垃圾桶注册包" + value);
                        break;
                    case "01":
                        logger.info("垃圾桶>>>" + deviceId + "<<<<<<<<<心跳");
                        byte[] msg1 = new byte[13];
                        msg1[0] = (byte) 0x68;
                        msg1[1] = Util.hexToByte(String.valueOf(deviceId));
                        msg1[2] = (byte) 0x00;
                        msg1[3] = (byte) 0x00;
                        msg1[4] = (byte) 0x00;
                        msg1[5] = (byte) 0x01;
                        msg1[6] = (byte) 0x04;
                        msg1[7] = (byte) 0x00;
                        long ms = DateUtil.getMS(DateUtil.getTime()) / 1000;
                        String hexms = Long.toHexString(ms);
                        msg1[11] = Util.hexToByte(hexms.substring(0, 2));
                        msg1[10] = Util.hexToByte(hexms.substring(2, 4));
                        msg1[9] = Util.hexToByte(hexms.substring(4, 6));
                        msg1[8] = Util.hexToByte(hexms.substring(6, 8));
                        byte sum1 = 0;
                        for (int i = 0; i < msg1.length; i++) {
                            sum1 += Util.getUnsignedByte(msg1[i]);
                        }
                        msg1[12] = (byte) ((byte) ~sum1 + 1);
                        String value1 = Util.BinaryToHexStrings(msg1);
                        byte[] s1 = null;
                        s = Util.hexStrToBinaryStr(value1);
                        ByteBuf message1 = Unpooled.copiedBuffer(s);
                        ctx.writeAndFlush(message1);//返回信息
                        map.put(deviceId, ctx);
                        clientOvertimeMap.put(ctx,0);
                       // logger.info("已发送心跳包" + value1);
                        break;
                    case "0f":
                        logger.info("接收到垃圾桶返回状态" + msgType);
                        Integer t1 = Integer.parseInt(prefixValue.substring(20, 22), 16);
                        Integer t2 = Integer.parseInt(prefixValue.substring(22, 24), 16);
                        Integer t3 = Integer.parseInt(prefixValue.substring(24, 26), 16);
                        Integer t4 = Integer.parseInt(prefixValue.substring(26, 28), 16);
                            String num = String.valueOf(t1) + "," + String.valueOf(t2) + "," + String.valueOf(t3) + "," + String.valueOf(t4);
                            redisTemplate.opsForValue().set(RedisConst.DEVICE_TRASH_NUM + deviceId, num);
                        break;
                    case "FF":
                        logger.info("垃圾桶>>>" + deviceId + "<<<<<<<<<开桶了");
                        break;
                }
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {//超时事件
            IdleStateEvent idleEvent = (IdleStateEvent) evt;
            InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
            String ip = ipSocket.getAddress().getHostAddress();
            ChannelId id1 = ctx.channel().id();
            int readIdleTimes = 0;
            try {
                readIdleTimes = clientOvertimeMap.get(ctx);
            } catch (Exception e) {

            }
            if (idleEvent.state().equals(IdleState.READER_IDLE)) {
                // 空闲40s之后触发 (心跳包丢失)
                if (readIdleTimes >= 3) {
                    // 连续丢失3个心跳包 (断开连接)
                    //清0

                    ctx.channel().close().sync();
                    clientOvertimeMap.remove(ctx);
                    //获取缓存键值
                    Set<String> keys = redisTemplate.keys(RedisConst.DEVICE_TRASH_IP + "*");
                    for (String key : keys) {
                        String ip1 = (String) redisTemplate.opsForValue().get(key);
                        if ((ip+id1.toString()).equals(ip1)) {
                            String[] split = key.split(":");

                           String id = split[split.length-1];
                            trashService.updateDevice(id);
                        }
                    }

                    logger.error("已与" + ctx.channel().remoteAddress() + "断开连接");
                } else {
                    readIdleTimes++;
                    clientOvertimeMap.put(ctx, readIdleTimes);
                    logger.info(ctx.channel().remoteAddress() + "丢失了第 " + readIdleTimes + " 个心跳包");
                }
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    public static String convertByteBufToString(ByteBuf buf) {
        String str;

        if (buf.hasArray()) { // 处理堆缓冲区
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }
}
