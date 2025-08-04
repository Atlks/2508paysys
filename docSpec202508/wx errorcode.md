
微信支付 V3 下单常见错误码一览
状态码	错误码	描述	解决方案建议
400	PARAM_ERROR	参数错误	检查字段格式、必填项、长度限制等
400	APPID_MCHID_NOT_MATCH	AppID 与商户号不匹配	确认 AppID 是否绑定该商户号
400	MCH_NOT_EXISTS	商户号不存在	检查商户号是否正确
401	SIGN_ERROR	签名错误	检查签名串构造、私钥、换行符等
403	NO_AUTH	商户无权限	登录商户平台申请接口权限
403	OUT_TRADE_NO_USED	商户订单号重复	更换订单号重新下单

429	FREQUENCY_LIMITED	请求频率超限	降低调用频率，避免高并发
500	SYSTEM_ERROR	系统异常	稍后重试，或联系微信技术支持`



