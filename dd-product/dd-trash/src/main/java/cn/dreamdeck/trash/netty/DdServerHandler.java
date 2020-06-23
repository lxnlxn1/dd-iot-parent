package cn.dreamdeck.trash.netty;

import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.data.Util;
import cn.dreamdeck.model.trash.DdTrash;

import cn.dreamdeck.trash.service.DdTrashService;
import cn.dreamdeck.trash.service.RedisService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//物联网 开启检测端口 并写入数据库
@Component
@Sharable
public class DdServerHandler extends ChannelInboundHandlerAdapter {


    //  将当前客户端连接 存入map   实现控制设备下发 参数
    public static Map<String, ChannelHandlerContext> map = new ConcurrentHashMap<String, ChannelHandlerContext>();

    private Map<ChannelHandlerContext,Integer> clientOvertimeMap = new ConcurrentHashMap<>();

    @Resource
    private RedisService redisService;
    //返回结果map
    public static Map<String, String> resuleMap = new ConcurrentHashMap<String, String>();

    private static final Logger logger = LoggerFactory.getLogger(DdServerHandler.class);

    //心跳次数
    private AtomicInteger readIdleTimes = new AtomicInteger();

   @Autowired
   private DdTrashService trashService;

    @Autowired
    private RedisTemplate redisTemplate;

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // String url = ctx.channel().remoteAddress().toString();
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = ipSocket.getAddress().getHostAddress();
        map.put(ip, ctx);
        System.out.println("有新客户端连接接入。。。" + ctx.channel().remoteAddress() + " " + ctx.channel().id());
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = ipSocket.getAddress().getHostAddress();
        map.remove(ip);
        String type = (String) redisTemplate.opsForHash().get(ip, "type");
        String deviceId = (String) redisTemplate.opsForHash().get(ip, "deviceId");


        System.out.println("失去连接" + ip + type);
    }

    /**
     * 获取数据
     *
     * @param ctx  上下文
     * @param msgs 获取的数据
     * @throws UnsupportedEncodingException
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msgs) throws UnsupportedEncodingException, InterruptedException {


        String url = ctx.channel().remoteAddress().toString();//设备请求地址（个人将设备的请求地址当作 map 的key）
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = ipSocket.getAddress().getHostAddress();
        //  int port = ipSocket.getPort();
        logger.info("请求地址" + url);

        //msg为接收到的客户端传递的数据   个人这边直接传的json 数据
        ByteBuf readMessage = (ByteBuf) msgs;
        //解析客户端json 数据
        //JSONObject json=JSONObject.fromObject(readMessage.toString(CharsetUtil.UTF_8));
        String prefixValue = ByteBufUtil.hexDump(readMessage);
        System.out.println("接收到的数据" + prefixValue);
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
                String msgType = prefixValue.substring(10, 12);//消息类型
                logger.info("垃圾桶>>>" + deviceId + "消息类型" + msgType);
                    if (msgType.equals("00")) {
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
                        DdTrash tm = redisService.getTrashData(ip);
                        tm.setDeviceIp(ip);
                        tm.setStatus(1);
                        redisService.saveTrashData(tm);
                        trashService.saveDevice(tm);
                        logger.info("已发送垃圾桶注册包" + value);
                    } else if (msgType.equals("01")) {
                        logger.info("垃圾桶>>>" + deviceId + "<<<<<<<<<心跳");
                        byte[] msg = new byte[13];
                        msg[0] = (byte) 0x68;
                        msg[1] = Util.hexToByte(String.valueOf(deviceId));
                        msg[2] = (byte) 0x00;
                        msg[3] = (byte) 0x00;
                        msg[4] = (byte) 0x00;
                        msg[5] = (byte) 0x01;
                        msg[6] = (byte) 0x04;
                        msg[7] = (byte) 0x00;
                        long ms = DateUtil.getMS(DateUtil.getTime()) / 1000;
                        String hexms = Long.toHexString(ms);
                        logger.info(hexms);
                        msg[11] = Util.hexToByte(hexms.substring(0, 2));
                        msg[10] = Util.hexToByte(hexms.substring(2, 4));
                        msg[9] = Util.hexToByte(hexms.substring(4, 6));
                        msg[8] = Util.hexToByte(hexms.substring(6, 8));
                        byte sum = 0;
                        for (int i = 0; i < msg.length; i++) {
                            sum += Util.getUnsignedByte(msg[i]);
                        }
                        msg[12] = (byte) ((byte) ~sum + 1);
                        String value = Util.BinaryToHexStrings(msg);
                        byte[] s = null;
                        s = Util.hexStrToBinaryStr(value);
                        ByteBuf message = Unpooled.copiedBuffer(s);
                        ctx.writeAndFlush(message);//返回信息

                        logger.info("已发送心跳包" + value);


                    } else if (msgType.equals("0f")) {
                        logger.info("接收到垃圾桶返回状态" + msgType);
                        Integer t1 = Integer.parseInt(prefixValue.substring(20, 22), 16);
                        Integer t2 = Integer.parseInt(prefixValue.substring(22, 24), 16);
                        Integer t3 = Integer.parseInt(prefixValue.substring(24, 26), 16);
                        Integer t4 = Integer.parseInt(prefixValue.substring(26, 28), 16);

                        DdTrash tm = redisService.getTrashData("1");

                        tm.setTrashFullList("," + String.valueOf(t1) + "," + String.valueOf(t2) + "," + String.valueOf(t3) + "," + String.valueOf(t4) + ",");
                        resuleMap.put(ip,tm.getTrashFullList());
                        redisService.saveTrashData(tm);
                    } else if (msgType.equals("FF")) {
                        logger.info("垃圾桶>>>" + deviceId + "<<<<<<<<<开桶了");
                    }
                }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {//超时事件
            IdleStateEvent idleEvent = (IdleStateEvent) evt;
            InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
            String ip = ipSocket.getAddress().getHostAddress();
            if (idleEvent.state().equals(IdleState.READER_IDLE)) {
                // 空闲40s之后触发 (心跳包丢失)
                if (readIdleTimes.get() >= 3) {
                    // 连续丢失3个心跳包 (断开连接)
                    ctx.channel().close().sync();
                    trashService.updateDevice(ip);
                    logger.error("已与" + ctx.channel().remoteAddress() + "断开连接");
                    System.out.println("已与" + ip + "断开连接");
                } else {
                    readIdleTimes.incrementAndGet();
                    logger.debug(ctx.channel().remoteAddress() + "丢失了第 " + readIdleTimes.get() + " 个心跳包");
                    System.out.println("丢失了第 " + readIdleTimes.get() + " 个心跳包");
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
