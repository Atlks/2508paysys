package model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
@Data
@FieldNameConstants
public class paymentDto {

    public String transactionId;
    public BigDecimal amount = BigDecimal.ZERO;

}
