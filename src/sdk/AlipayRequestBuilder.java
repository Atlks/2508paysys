package sdk;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AlipayRequestBuilder {

    public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap<>();

        // 公共参数
        params.put("app_id", "2014072300007148");
        params.put("method", "alipay.trade.app.pay");
        params.put("format", "JSON");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", "2025-07-31 13:56:00");
        params.put("version", "1.0");
        params.put("notify_url", "https://yourdomain.com/notify");

        // 业务参数（biz_content 需转成 JSON 字符串）
        String bizContent = """
        {
            "subject":"大乐透",
            "out_trade_no":"70501111111S001111119",
            "total_amount":"9.00",
            "product_code":"QUICK_MSECURITY_PAY"
        }
        """;
        params.put("biz_content", bizContent);

        // Step 1: 对参数进行排序（TreeMap 可自动按 key 排序）
        Map<String, String> sortedParams = new TreeMap<>(params);

        // Step 2: 构造待签名字符串
        StringBuilder content = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (content.length() > 0) content.append("&");
            content.append(entry.getKey()).append("=").append(entry.getValue());
        }

        // Step 3: 使用私钥生成签名（示例中伪造签名，实际需用支付宝工具类）
        String sign = "伪造签名字符串"; // 例如用 AlipaySignature.rsaSign(content.toString(), privateKey, "utf-8", "RSA2");

        // Step 4: URL 编码参数值并拼接完整请求字符串
        StringBuilder finalRequest = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            finalRequest.append(entry.getKey())
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .append("&");
        }
        finalRequest.append("sign=").append(URLEncoder.encode(sign, StandardCharsets.UTF_8));

        System.out.println("支付请求字符串：\n" + finalRequest);
    }
}

