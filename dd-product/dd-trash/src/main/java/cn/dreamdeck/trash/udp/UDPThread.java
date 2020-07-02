//package cn.dreamdeck.trash.udp;
//
//
//import cn.dreamdeck.common.data.Util;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.net.*;
//
//public class UDPThread extends Thread {
//    private static final Logger logger = LoggerFactory.getLogger(UDPThread.class);
//
//    String broadcast = "";
//
//    boolean isCircle = false;
//
//    public UDPThread(String broadcast, boolean isCircle) {
//        this.broadcast = broadcast;
//        this.isCircle = isCircle;
//    }
//
//    @Override
//    public void run() {
//        if (isCircle){
//            while (true) {
//                logger.info("发送UDP广播>>>>>" + broadcast);
//
//                try {
//                    DatagramSocket ds = new DatagramSocket();// 创建用来发送数据报包的套接字
//
//                    byte[] msg = Util.hexStrToBinaryStr(broadcast);
//
//                    DatagramPacket dp = new DatagramPacket(msg,
//                            msg.length,
//                            InetAddress.getByName("255.255.255.255"), 8234);
//                    // 构造数据报包，用来将长度为 length 的包发送到指定主机上的指定端口号
//                    try {
//                        ds.send(dp);
//                        logger.info("广播已发送");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    ds.close();
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                } catch (SocketException e) {
//                    e.printStackTrace();
//                }
//            }
//        }else {
//            logger.info("发送UDP广播>>>>>");
//
//            try {
//                DatagramSocket ds = new DatagramSocket();// 创建用来发送数据报包的套接字
//
//                byte[] msg = Util.hexStrToBinaryStr(broadcast);
//
//                DatagramPacket dp = new DatagramPacket(msg,
//                        msg.length,
//                        InetAddress.getByName("255.255.255.255"), 8234);
//                // 构造数据报包，用来将长度为 length 的包发送到指定主机上的指定端口号
//                try {
//                    ds.send(dp);
//                    logger.info("已发送UDP广播>>>>>");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                ds.close();
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            } catch (SocketException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}
