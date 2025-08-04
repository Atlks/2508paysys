//package sdk;
//
//import com.stripe.Stripe;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;
//
//public class StripeCheckoutExample {
//    public static void main(String[] args) throws Exception {
//        // 设置你的 Stripe Secret Key（建议从环境变量读取）
//        Stripe.apiKey = "sk_test_你的密钥";
//
//        // 构造支付会话参数
//        SessionCreateParams params = SessionCreateParams.builder()
//                .setMode(SessionCreateParams.Mode.PAYMENT)
//                .setSuccessUrl("https://yourdomain.com/success")
//                .setCancelUrl("https://yourdomain.com/cancel")
//                .addLineItem(
//                        SessionCreateParams.LineItem.builder()
//                                .setQuantity(1L)
//                                .setPriceData(
//                                        SessionCreateParams.LineItem.PriceData.builder()
//                                                .setCurrency("usd")
//                                                .setUnitAmount(900L) // 单位为分，$9.00
//                                                .setProductData(
//                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                                                .setName("大乐透")
//                                                                .build()
//                                                )
//                                                .build()
//                                )
//                                .build()
//                )
//                .build();
//
//        // 创建支付会话
//        Session session = Session.create(params);
//
//        // 输出会话 ID，可用于前端跳转
//        System.out.println("Checkout Session ID: " + session.getId());
//        System.out.println("Checkout URL: " + session.getUrl());
//    }
//}
