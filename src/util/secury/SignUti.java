package util.secury;

import util.ex.SignatureVerificationException;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.codehaus.groovy.runtime.EncodingGroovyMethods.md5;
import static util.msc.HttpUti.toHttpMapKvMode;

public class SignUti {

    /**
     * 验证签证的方法，使用alipay模式，排序参数，这里使用md5来签名算法
     */
    public static void chkSign(Map<String, List<String>> stringListMap, String privateKey4merchat) throws NoSuchAlgorithmException {


        Map<String, String> httpmap = toHttpMapKvMode(stringListMap);
        String signFromurl = httpmap.get("sign");


        String sign = getSign(httpmap, privateKey4merchat);

        if (!sign.equals(signFromurl))
            throw new SignatureVerificationException("");
    }

    public static String getSign(Map<String, String> httpmap, String privateKey4merchat) throws NoSuchAlgorithmException {
        // Step 1: 对参数进行排序（TreeMap 可自动按 key 排序）
        Map<String, String> sortedParams = new TreeMap<>(httpmap);

        // Step 2: 构造待签名字符串
        httpmap.remove("sign");
        StringBuilder content = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (content.length() > 0) content.append("&");
            content.append(entry.getKey()).append("=").append(entry.getValue());
        }
        //  content.append(privateKey4merchat);

        // Step 3: 使用私钥生成签名（示例中伪造签名，实际需用支付宝工具类）
        String sign =getSign(content.toString(),privateKey4merchat);
        // 例如用 AlipaySignature.rsaSign(content.toString(), privateKey, "utf-8", "RSA2");
        return sign;
    }

    public static String getSign(String content, String privateKey) throws NoSuchAlgorithmException {

        return   md5(content+privateKey);
    }

}
