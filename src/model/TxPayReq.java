package model;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;


/**
 * use wcht fmt
 */
@Entity
@Table(name = "transactions_pay_req")
@DynamicUpdate
@DynamicInsert
@Data
@NoArgsConstructor
@FieldNameConstants
@TableName("transactions_pay_req")
public class TxPayReq {

    @Id
    private String outTradeNo;
    private BigDecimal amount;
    private String appid;
    private String mchid;
    private String description;
   public  String txId;
    private String notify_url;
    public String notifyRespStat;
    public String payTimestamp;
    public String payNonce;
    public String paySign;



}
