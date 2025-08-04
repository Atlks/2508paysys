
统一下单 POST https://api.mch.weixin.qq.com/v3/pay/transactions/native 除了native 还有哪些模式
微信支付 V3 接口除了 Native 模式（扫码支付），还支持多种支付模式，适用于不同的终端和场景。下面是完整的支付模式列表👇

💡 微信支付 V3 支持的支付模式
模式名称	trade_type	适用场景说明
JSAPI支付	JSAPI	微信内网页、小程序中发起支付，需用户授权（需传 openid）
Native支付	NATIVE	PC网页、广告、线下扫码等场景，生成二维码供用户扫码支付
APP支付	APP	移动 App 中集成微信 SDK，调起微信支付模块
H5支付	MWEB	手机浏览器中发起支付，跳转微信支付页面（需传 scene_info）
付款码支付	MICROPAY	用户出示付款码，商户扫码完成支付，适用于线下收银场景
🧩 接口地址示例（V3）
模式	接口地址
JSAPI	POST https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi
Native	POST https://api.mch.weixin.qq.com/v3/pay/transactions/native
APP	POST https://api.mch.weixin.qq.com/v3/pay/transactions/app
H5	POST https://api.mch.weixin.qq.com/v3/pay/transactions/h5
MICROPAY	POST https://api.mch.weixin.qq.com/v3/pay/transactions/micropay
每种模式的参数略有不同，比如：

JSAPI 需要传 openid

H5 需要传 scene_info

APP 适合移动端 SDK 调用

MICROPAY 需要传用户付款码 auth_code

如果你告诉我你的业务场景，我可以帮你选最合适的模式