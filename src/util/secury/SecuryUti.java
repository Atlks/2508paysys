package util.secury;

import util.ex.INVALID_PARAMETER;
import util.ex.UnauthorizedNonceBlank;
import util.ex.UnauthorizedNonceNull;
import util.ex.paypltfm.UnauthorizedNonceReplay;

import java.io.File;
import java.time.Duration;
import java.time.OffsetDateTime;

import static util.algo.NullUtil.isBlank;
import static util.uti.Uti.write;

public class SecuryUti {



    public static void chkNonce(String nonce) throws UnauthorizedNonceReplay, UnauthorizedNonceNull, UnauthorizedNonceBlank {

        if (isBlank(nonce))
            throw new UnauthorizedNonceBlank();
        String pathname = "nonce_" + nonce;
        if (new File(pathname).exists())
            throw new UnauthorizedNonceReplay("UnauthorizedNonceReplay="+nonce);
        write(pathname, "nonce111");

    }
    /**
     * 验证 timestamp 是否在有效时间窗口内
     *
     * @param timestamp ISO 8601 格式的时间戳字符串（例如：2025-08-04T11:05:00+08:00）
     * @return 是否在有效期内
     */
    public static boolean chkTmstmpTimeWin(String timestamp, long MAX_REQUEST_AGE_MINUTES) throws TimestampTimeWinChkException, INVALID_PARAMETER {

        if (isBlank(timestamp))
            throw new INVALID_PARAMETER("pay_timestamp");

        // 最大允许时间差（单位：分钟）
        //  long MAX_REQUEST_AGE_MINUTES = 35;



        OffsetDateTime requestTime = OffsetDateTime.parse(timestamp);
        OffsetDateTime now = OffsetDateTime.now();

        Duration diff = Duration.between(requestTime, now);

        boolean b = !diff.isNegative() && diff.toMinutes() <= MAX_REQUEST_AGE_MINUTES;

        if (b == false)
            throw new TimestampTimeWinChkException();


        return b;


    }


}
