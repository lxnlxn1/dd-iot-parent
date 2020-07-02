//package cn.dreamdeck.trash.udp;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SocketThread extends Thread implements InitializingBean {
//	private static final Logger logger = LoggerFactory.getLogger(SocketThread.class);
//    @Autowired
//    @Override
//    public void run() {
//
//        String broadcast = "02FE0a010b8e2e0c";
//        //String broadcast = "02FE0a010ba02e0c";
//        //调用UDP 广播线程
//        UDPThread udpThread = new UDPThread(broadcast, false);
//        udpThread.start();
//
//        try {
//            sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        start();
//    }
//}