package model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Entity
@Table(name = "AlipayPaymtRequests")
@DynamicUpdate
@DynamicInsert
@Data
@NoArgsConstructor
@FieldNameConstants
public class AlipayPaymtRequest {
    // 公共参数
    private String appId;
    private String method;
    private String format;
    private String charset;
    private String signType;
    private String timestamp;
    private String version;
    private String notifyUrl;
    private String subject;

    @Id
    private String outTradeNo;
    private BigDecimal totalAmount;

//    @Embedded
//    private BizContent bizContent;
//
//    @Embeddable
//    public static class BizContent {
//        private String subject;
//        private String outTradeNo;
//        private String totalAmount;
//        private String productCode;
//
//        // Getters and Setters
//        public String getSubject() { return subject; }
//        public void setSubject(String subject) { this.subject = subject; }
//
//        public String getOutTradeNo() { return outTradeNo; }
//        public void setOutTradeNo(String outTradeNo) { this.outTradeNo = outTradeNo; }
//
//        public String getTotalAmount() { return totalAmount; }
//        public void setTotalAmount(String totalAmount) { this.totalAmount = totalAmount; }
//
//        public String getProductCode() { return productCode; }
//        public void setProductCode(String productCode) { this.productCode = productCode; }
//    }

//   public String getMethod() { return method; }
//    public void setMethod(String method) { this.method = method; }
//
//    public String getFormat() { return format; }
//    public void setFormat(String format) { this.format = format; }
//
//    public String getCharset() { return charset; }
//    public void setCharset(String charset) { this.charset = charset; }
//
//    public String getSignType() { return signType; }
//    public void setSignType(String signType) { this.signType = signType; }
//
//    public String getTimestamp() { return timestamp; }
//    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
//
//    public String getVersion() { return version; }
//    public void setVersion(String version) { this.version = version; }
//
//    public String getNotifyUrl() { return notifyUrl; }
//    public void setNotifyUrl(String notifyUrl) { this.notifyUrl = notifyUrl; }

//    public BizContent getBizContent() { return bizContent; }
//    public void setBizContent(BizContent bizContent) { this.bizContent = bizContent; }
}
