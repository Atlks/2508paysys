

符合 RESTful 架构风格。你可以直接通过 Java 后端发起 HTTP 请求来调用这些接口，而不一定非要用官方 SDK。

🌐 常用微信支付 REST 接口（V3）
接口功能	请求方式	接口地址
统一下单	POST	https://api.mch.weixin.qq.com/v3/pay/transactions/native
查询订单	GET	https://api.mch.weixin.qq.com/v3/pay/transactions/id/{transaction_id}



关闭订单	POST	https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/{out_trade_no}/close
申请退款	POST	https://api.mch.weixin.qq.com/v3/refund/domestic/refunds
查询退款	GET	https://api.mch.weixin.qq.com/v3/refund/domestic/refunds/{refund_id}