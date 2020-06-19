package cn.dreamdeck.iot.project;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Test {

    public static void main(String[] args) {
        String s = decryptAES("$2a$10$JmDSzCWt3zfN0WcfjW1ZZ.Brp7ngLSHhLnlIpY988LRL7Lg8X0cpi","1234567812345678");
        System.out.println(s);
    }

    private static String decryptAES(String data, String pass) {
        AES aes = new AES(Mode.CBC, Padding.NoPadding,
                new SecretKeySpec(pass.getBytes(), "AES"),
                new IvParameterSpec(pass.getBytes()));
        byte[] result = aes.decrypt(Base64.decode(data.getBytes(StandardCharsets.UTF_8)));
        return new String(result, StandardCharsets.UTF_8);
    }
}
