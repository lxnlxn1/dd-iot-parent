package cn.dreamdeck.common.data;


import cn.dreamdeck.common.data.config.WxPayConfigs;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;

/**
 * @Title: AESUtil.java
 * @Package com.yiyong.mavenspring.demo.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Yiyong Wu
 * @date 2015年12月29日 下午2:38:00
 * @version V1.0
 */


public class AESUtil {  
  
	/** 
     * 密钥算法 
     */  
    private static final String ALGORITHM = "AES";  
    /** 
     * 加解密算法/工作模式/填充方式 
     */  
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";  
    /** 
     * 生成key 
     */  
    private static SecretKeySpec key = new SecretKeySpec(MD5Util.MD5Encode(WxPayConfigs.partnerkey, "UTF-8").toLowerCase().getBytes(), ALGORITHM);

  
    /** 
     * AES加密 
     *  
     * @param data
     * @return
     * @throws Exception 
     */  
    public static String encryptData(String data) throws Exception {
        // 创建密码器  
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);  
        // 初始化  
        cipher.init(Cipher.ENCRYPT_MODE, key);  
        return Base64Utils.encode(cipher.doFinal(data.getBytes()));
    }  
  
    /** 
     * AES解密 
     *  
     * @param base64Data 
     * @return 
     * @throws Exception 
     */  
    public static String decryptData(String base64Data) throws Exception {  
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);  
        cipher.init(Cipher.DECRYPT_MODE, key);  
        return new String(cipher.doFinal(Base64Utils.decode(base64Data)));
    }  
  
    public static String decrypt(String secretKey, String ivKey, byte[] content) throws UnsupportedEncodingException{
        String encryptMode = "AES/CBC/PKCS7Padding";
        
        byte[] secrecKeyByte = secretKey.getBytes("ASCII");
       
        String decryptContent = null;
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecretKeySpec keyspec = new SecretKeySpec(secrecKeyByte, "AES");
            Cipher cipher = Cipher.getInstance(encryptMode, "BC");
            
            IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes("ASCII"));
            cipher.init(Cipher.DECRYPT_MODE, keyspec, iv);
            byte[] byte_content = cipher.doFinal(content);

            decryptContent = new String(byte_content, "utf-8");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return decryptContent;
    }
    
    

    public static String encrypt(String secretKey ,String ivKey,String vector) throws Exception {

        if(secretKey == null) {
            return null;
        }
        if(secretKey.length() != 16) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(vector.getBytes("utf-8"));
        return Base64.encodeBase64String(encrypted);
    }
    
    public static void main(String[] args) throws Exception {  
        String A = "oSWxqqMF5lk2EF+gdrdt5wPrOru854za5XjHq5cUXs74zF9+jlxGOo7DHQIuntolVF3kQAruoMoNK5lLRsCulgG2hAT+6sNen8f/f3drMxfsTFOj3aBTKkIHs2p3AVJA4fXpGRCpejq3JJplSQnnSwFljzcxvqe7rU3y/H0KpFyBuYUSEf+msbkHEnHnIHQi4p9JDlLPWoKHramM7R65Qd13GdUU41scNybWCkwl+q/cY2Nv6KUt490JXTbTEgZNE6ArJKGg9woRMUdJEimTnv2OSY16yjo8dlIiozEoHcoQsvSFuMA5DHfHmtk5gbn8y6FVLHbt8XmmOIkfl/CVCXGQ+fGJmazxmqpTLBnAxXogFX2c2h8ZFqrWHW0wWZNSqpRX8HnMBw4V5hUMCiN9ASP3AzkpbtxdkDaeJYagVFgpB7oXxNUlQMy7pCqWCqbhoeLlZtzACx3qNqf57cQLn06T8wrYddf3f78oIYceVWMBses6wcJW2uTUdci4hYOQn5G+iVGLRzMuI8xwQSeBtdrWBor842tEsg4/wgFRxiEgjN+Jl+pCbwULjzt870OwC/UKD9mM3bhyay1jxeKNfkqgks0TH9eZXT1mR6IBfIUipgk9nTrGLFQwt4AAAf7/KoW7A3d1eYGY1vo/QkinixiZsxOJhzw95X6wiiARPa8oe0180lCuhLtIrNRlxyVMbbwA8GQVuCCE6w+/yKIF+el+Gcf7Gm2ljQzV7PEwiomW/DsBqUb5mwGfI52NLRa70kJ8vgaXeMN1xhwWYLzg02muvGGwS2P4kgGO0Sg0L5ycpN7Vp421+HnAPdcW6y/pQi03BKAR6fZT5JQYAIoNN4K8K6ZbgfZiuG32q0q4bwVWrg4jBlyPmj8JwHtbikbAgoJ9sUwWYi7P+Btk1ZHCPLW90p+1mIL8eVpneOaon3mSW0R4JDiIJK8oYLD/1n4NTKRTg9c6OMdSHnK8BUnodw==";  
        String B = AESUtil.decryptData(A);  
        System.out.println(B);  
    }  
  
}  
