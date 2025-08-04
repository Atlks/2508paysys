package sdk;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


//只能https，否则不安全
public class StripeRestExample {

    public static void main(String[] args) throws Exception {
        String apiKey = "sk_test_你的密钥"; // 你的 Stripe Secret Key
        URL url = new URL("https://api.stripe.com/v1/checkout/sessions");

        String params = "success_url=https://yourdomain.com/success" +
                "&cancel_url=https://yourdomain.com/cancel" +
                "&mode=payment" +
                "&line_items[0][price_data][currency]=usd" +
                "&line_items[0][price_data][product_data][name]=大乐透" +
                "&line_items[0][price_data][unit_amount]=900" +
                "&line_items[0][quantity]=1";

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);

        try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
            out.writeBytes(params);
            out.flush();
        }

        // 读取返回结果
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println("响应结果: " + response.toString());
        }
    }
}
