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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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


    /**
     * 交易限额
     */
    @Min(1)  @Max(999999)
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
