package sdk;

import okhttp3.*;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class WxPayV3JsonExample {

    private static final String MCH_ID = "190000****";
    private static final String APP_ID = "wxa9d9651ae******";
    private static final String API_V3_KEY = "your_api_v3_key";
    private static final String SERIAL_NO = "5157F09EFDC096DE15EBE81A47057A72";
    private static final String PRIVATE_KEY_PATH = "/path/to/apiclient_key.pem";
    private static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";

    public static void main(String[] args) throws Exception {
        // 构造请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("appid", APP_ID);
        requestBody.put("mchid", MCH_ID);
        requestBody.put("description", "大乐透");
        requestBody.put("out_trade_no", "order_20250731_001");
        requestBody.put("notify_url", "https://yourdomain.com/wechat/notify");

        Map<String, Object> amount = new HashMap<>();
        amount.put("total", 900); // 单位：分
        requestBody.put("amount", amount);

        Gson gson = new Gson();
        String json = gson.toJson(requestBody);

        // 构造签名（略，需使用 SHA256-RSA 签名）
        String authorization = "WECHATPAY2-SHA256-RSA2048 ..."; // 你需要根据微信规则生成

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UNIFIED_ORDER_URL)
                .addHeader("Authorization", authorization)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Wechatpay-Serial", SERIAL_NO)
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        System.out.println("微信返回结果：");
        System.out.println(responseBody);
    }
}
