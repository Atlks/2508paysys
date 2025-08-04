package util.msc;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.algo.ToXX.toCamelName;
import static util.uti.Uti.encodeJson;
import static util.uti.Uti.fieldSetVal;

public class HttpUti {

    public static String convertToJson(Object dto) {
        return encodeJson(dto);
    }


    public static Map<String, String> toHttpMapKvMode(Map<String, List<String>> stringListMap) {

        Map<String, String> result = new HashMap<>();
        if (stringListMap == null || stringListMap.isEmpty()) {
            return result;
        }

        for (Map.Entry<String, List<String>> entry : stringListMap.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            if (values != null && !values.isEmpty()) {
                result.put(key, values.get(0)); // 取第一个元素
            } else {
                result.put(key, ""); // 无值则设为空字符串
            }
        }

        return result;
    }

    /**
     * @param notifyUrl

     */
    public static String postHttp(String notifyUrl, String postContext,String sign) {
        {
            try {


                // 创建 HttpClient 实例
                HttpClient client = HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(60))
                        .build();

                // 构建 POST 请求
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(notifyUrl))
                        .header("Content-Type", "application/json")
                        .header("sign", sign)

                        .POST(HttpRequest.BodyPublishers.ofString(postContext))
                        .build();

                // 发送请求并获取响应
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println("Response Code: " + response.statusCode());
                System.out.println("Response Body: " + response.body());

                return response.body();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

    }


    public static <dtoType> dtoType toDto(Map<String, List<String>> httpMp, Class<dtoType> dtoCls) throws Exception {

        if (dtoCls.equals(Object.class)) {
            return (dtoType) httpMp; // 允许返回 Map
        }
        if (Map.class.isAssignableFrom(dtoCls)) {
            return dtoCls.cast(httpMp); // 直接转换为 Map
        }
        dtoType instance = dtoCls.getDeclaredConstructor().newInstance(); // 创建对象实例
        for (Object entry1 : httpMp.entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) entry1;
            String key = entry.getKey();
            String fldName=toCamelName(key);
            Field field = dtoCls.getDeclaredField(fldName); // 获取字段
            field.setAccessible(true); // 允许访问私有字段
            Object value = entry.getValue();
            List<String> li = (List<String>) value;
            String value1 = li.get(0);
            fieldSetVal(value1, field, instance);
            // field.set(instance, value1); // 赋值
        }
        return instance;

    }
}
