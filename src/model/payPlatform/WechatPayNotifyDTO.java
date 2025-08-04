package model.payPlatform;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 用 total 做订单金额校验和结算依据
 *
 * ✅ 用 payer_total 做用户支付行为分析（如是否使用优惠）
 *
 * ❌ 不建议用 payer_total 做订单金额校验，因为它可能因优惠而变化
 *
 *
 */
@Data
public class WechatPayNotifyDTO {
    private String mchid;
    private String appid;
    private String out_trade_no;
    private String transaction_id;
    private String trade_type;
    private String trade_state;  // trade_state：交易状态（通常是 SUCCESS）
    private Amount amount;
    private Payer payer;

    /**
     *  "success_time": "2023-06-07T20:14:28+08:00"
     */
    private String success_time;

    @Data
    public static class Amount {
        private BigDecimal total;//total 是你系统中订单的原始金额，通常用于 结算、对账、发票开具 等。


        /**
         * ayer_total 是用户实际从钱包或银行卡中扣除的金额，可能因为使用了 优惠券、立减活动、积分抵扣 等而低于 total。
         */
        private BigDecimal payer_total;  //用户实际支付金额 用户实际支付的金额
        private String currency;
        private String payer_currency;
    }

    @Data
    public static class Payer {
        private String openid;
    }
}
