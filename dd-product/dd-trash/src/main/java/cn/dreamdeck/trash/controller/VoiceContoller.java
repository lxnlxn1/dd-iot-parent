package cn.dreamdeck.trash.controller;


import cn.dreamdeck.common.data.AESUtil;
import cn.dreamdeck.common.data.Sha1Util;
import cn.dreamdeck.common.data.Util;
import cn.dreamdeck.model.trash.DdGarbageClassification;
import cn.dreamdeck.model.trash.DdTrash;
import cn.dreamdeck.trash.netty.DdServerHandler;
import cn.dreamdeck.trash.service.DdGarbageClassificationService;
import cn.dreamdeck.trash.service.DdTrashService;
import cn.dreamdeck.trash.service.RedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lxkui
 * @date 2019/11/11
 * 语音回复测试
 * <p>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/trash/aiui")
public class VoiceContoller {
    private static final String token = "d61c5d74300a2212";
    private static final String aeskey = "c379c37fd1dadff0";


    private static final Logger logger = LoggerFactory.getLogger(VoiceContoller.class);
    @Resource
    private DdGarbageClassificationService ddGarbageClassificationService;

    @Autowired
    private RedisService redisService;

    //使用次数
    private static Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();

    @Autowired
    private DdTrashService ddTrashService;
    @PostMapping(value = "/classify/{scene}")
    @ResponseBody

    public String find(@PathVariable("scene") String scene) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        //String ip = request.getRemoteAddr();
        //logger.info("接入语音垃圾桶外网IP："+outerIp);
        Enumeration<String> enu = request.getParameterNames();
        String classifyName = "";
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            classifyName += request.getParameter(name);
        }

      //  System.out.println("------------------------------------"+scene);
        String model =scene;
        DdTrash ddTrash = ddTrashService.selectByauiaModel(model);

        String ip = ddTrash.getDeviceIp();
        int num = 0;
        String s = DdServerHandler.resuleMap.get(ip);
        // String s = "20,20,20,20";
        String[] split = new String[0];
        try {
            split = s.split(",");
            if (split.length != 4) {
                return "网络连接错误，请联系管理员";
            }
        } catch (Exception e) {
            return "网络连接错误，请联系管理员";
        }
        DdGarbageClassification ddGarbageClassification = ddGarbageClassificationService.likeName(classifyName);
        if (ddGarbageClassification != null) {
            if (ddGarbageClassification.getCategory() == 1) {
                // System.out.println("这是可回收垃圾");

                if (Integer.parseInt(split[0],16)==100){
                    return "该桶已满，请联系管理员";
                }

                String value =  ddTrashService.openData(1,1);
                byte[] bytes =  Util.hexStrToBinaryStr(value);
                ByteBuf buf = Unpooled.wrappedBuffer(bytes);
                DdServerHandler.map.get(ip).channel().writeAndFlush(buf);


                if (map.get("可回收垃圾") == null) {
                    map.put("可回收垃圾", num);
                }
                int num1 = map.get("可回收垃圾");
                ++num1;
                map.put("可回收垃圾", num1);

                return "这是可回收垃圾，桶盖已开,垃圾分类从我做起";



            } else if (ddGarbageClassification.getCategory() == 2) {
                if (Integer.parseInt(split[1],16)==100){
                    return "该桶已满，请联系管理员";
                }


                String value =  ddTrashService.openData(1,2);
                byte[] bytes =  Util.hexStrToBinaryStr(value);
                ByteBuf buf = Unpooled.wrappedBuffer(bytes);
                DdServerHandler.map.get(ip).channel().writeAndFlush(buf);

                if (map.get("有害垃圾") == null) {
                    map.put("有害垃圾", num);
                }
                int num2 = map.get("有害垃圾");
                ++num2;
                map.put("有害垃圾", num2);
                return "这是有害垃圾，桶盖已开,垃圾分类从我做起";

            } else if (ddGarbageClassification.getCategory() == 4) {
                //     return "这是厨余垃圾，桶盖已开垃圾分类从我做起";



                if (Integer.parseInt(split[2],16)==100){
                    return "该桶已满，请联系管理员";
                }
                String value =  ddTrashService.openData(1,3);
                byte[] bytes =  Util.hexStrToBinaryStr(value);
                ByteBuf buf = Unpooled.wrappedBuffer(bytes);
                DdServerHandler.map.get(ip).channel().writeAndFlush(buf);

                if (map.get("厨余垃圾") == null) {
                    map.put("厨余垃圾", num);
                }
                int num3 = map.get("厨余垃圾");
                ++num3;
                map.put("厨余垃圾", num3);
                return "这是厨余垃圾，桶盖已开，垃圾分类从我做起";

            } else if (ddGarbageClassification.getCategory() == 8) {
                // System.out.println("这是其他垃圾");
                if (Integer.parseInt(split[3],16)==100){
                    return "该桶已满，请联系管理员";
                }
                String value =  ddTrashService.openData(1,4);
                byte[] bytes =  Util.hexStrToBinaryStr(value);
                ByteBuf buf = Unpooled.wrappedBuffer(bytes);
                DdServerHandler.map.get(ip).channel().writeAndFlush(buf);
                if (map.get("其他垃圾") == null) {
                    map.put("其他垃圾", num);
                }
                int num4 = map.get("其他垃圾");
                ++num4;
                map.put("其他垃圾", num4);
                return "这是其他垃圾，桶盖已开，垃圾分类从我做起";
            }


        }
        return "我不知道你在说什么";

    }

    @GetMapping(value = "/list")
    @ResponseBody
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String rand = request.getParameter("rand");

        if (signature == null || timestamp == null || rand == null) {
            return;
        }
        if (signature.isEmpty() || rand.isEmpty() || timestamp.isEmpty()) {
            return;
        }

        //对三个参数进行字典排序
        List<String> list = new ArrayList<String>();

        list.add(token);
        list.add(timestamp);
        list.add(rand);
        Collections.sort(list);

        String localSig = "";
        for (int i = 0; i < list.size(); i++) {
            localSig += list.get(i);
        }


        String sigtureSha1 = Sha1Util.getSha1(localSig);
        System.out.println(signature + "给的");
        System.out.println(sigtureSha1 + "算的");
        if (sigtureSha1.equals(signature)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(Sha1Util.getSha1(token));
        } else {
            //这里可以不用发送，写入日志系统或告警组件即可，AIUI不会接收处理。
            response.getWriter().append("check token failed" + sigtureSha1);
        }
    }

    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);

    }


    @PostMapping(value = "/list")
    @ResponseBody
    //消息通信阶段
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        System.out.println(request + "进来了------------------------------------------------------------------------------");

        String encrypttype = request.getParameter("encrypttype");
        System.out.println(encrypttype);
        if (encrypttype.equals("aes")) {
            System.out.println("进第一个");
            int len = request.getContentLength();
            ServletInputStream inputStream = request.getInputStream();
            byte[] buffer = new byte[len];
            inputStream.read(buffer, 0, len);
            //FileUtils.saveBytesToFile(buffer);
            try {
                String content = AESUtil.decrypt(aeskey, aeskey, buffer);
                System.out.print(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //构建自定义格式数据给AIUI服务器，AIUI服务器全量下发给客户端。
            JSONObject customData = new JSONObject();
            customData.put("key", "custome");
            customData.put("content", "这是一条来自后处理的测试结果");

            System.out.println("处理后的" + customData.toString());
//
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().append(AESUtil.encrypt(aeskey, aeskey, customData.toString()));
//
//
//				response.setContentType("application/json;charset=utf-8");

        } else {
            System.out.println("进第er个");
            // get request body.
            ServletInputStream inputStream = request.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            StringBuilder aiuiPostData = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                aiuiPostData.append(line);
            }
            com.alibaba.fastjson.JSONObject aiuiPostJson = JSON.parseObject(aiuiPostData.toString());
            System.out.println("返回的json------------------------------" + aiuiPostJson);

            String sub = aiuiPostJson.getString("FromSub");

            String SessionParams = aiuiPostJson.getString("SessionParams");

            System.out.println(SessionParams);

            System.out.println(Base64.decodeBase64(SessionParams.getBytes()));


            JSONObject msgJson = aiuiPostJson.getJSONObject("Msg");
            String content = msgJson.getString("Content");


            content = new String(Base64.decodeBase64(content.getBytes()));
            System.out.println("content" + content);
            if (JSONObject.parseObject(content).getJSONObject("intent") == null) {

            } else {

                JSONObject contentInfo = JSONObject.parseObject(content).getJSONObject("intent");
                if (contentInfo == null) {
                    System.out.println(contentInfo);
                    JSONArray semantic = contentInfo.getJSONArray("semantic");
                    if (semantic == null) {
                        System.out.println(semantic);
                        for (int i = 0; i < semantic.size(); i++) {
                            JSONObject semanticObject = (JSONObject) semantic.get(i);
                            JSONArray array = semanticObject.getJSONArray("slots");
                            for (int j = 0; j < array.size(); j++) {
                                JSONObject obj = (JSONObject) array.get(j);
                                System.out.println(obj.get("value"));


                            }
                        }
                    }
                }


            }

            String userParam = aiuiPostJson.getString("UserParams");
            String userParams = new String(Base64.decodeBase64(userParam.getBytes()));

            System.out.println("userParams" + userParams);
            // do something what you wanted.
            // For example, request third platform or your own server, etc.

            // ....

            // build your custom data used to transmit to client(sdk/webapi) which can get
            // these data from sub "tpp". "key" and "content" are just samples, not necessary.
            JSONObject customData = new JSONObject();

            customData.put("key", "custom");
            customData.put("content", "笨蛋小d接受到多个结果");
            //repsonse to AIUI server
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().append(customData.toString());
            System.out.println("result" + customData.toString());
        }


    }

}
