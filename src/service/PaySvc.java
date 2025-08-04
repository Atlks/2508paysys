package service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityNotFoundException;
import model.*;
import model.payPlatform.WechatPayNotifyDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import util.ex.INVALID_PARAMETER;
import util.ex.UnauthorizedNonceBlank;
import util.ex.UnauthorizedNonceNull;
import util.ex.paypltfm.MCH_NOT_EXISTS;
import util.ex.paypltfm.OUT_TRADE_NO_USED;
import util.ex.paypltfm.UnauthorizedNonceReplay;
import util.msc.HttpUti;
import util.uti.context.ProcessContext;
import util.uti.context.ThreadContext;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.date.DateUtil.now;
import static org.codehaus.groovy.runtime.EncodingGroovyMethods.md5;
import static util.StrUti.extractLastPathSegment;
import static util.algo.GetUti.getUuid;
import static util.algo.NullUtil.isBlank;
import static util.msc.HttpUti.convertToJson;
import static util.secury.SignUti.getSign;
import static util.uti.Uti.encodeJson;
import static util.uti.Uti.write;
import static util.uti.orm.JpaUti.*;

/**
 * 支付订单服务
 */
public class PaySvc {


    /**
     * 发起订单
     * http://localhost:8888/v3/pay/transactions/native?out_trade_no=666&amount=88&mchid=mmmcht_id
     * http://localhost:8888/apiv1/pay?out_trade_no=666&total_amount=88
     *
     * @param tx
     * @return
     */
    @PermitAll
    //  @RolesAllowed({"adm","opr"})
    public static Object pay(TxPayReq tx) throws Exception, OUT_TRADE_NO_USED, UnauthorizedNonceNull {

        //
        chkTmstmpTimeWin(tx.getPayTimestamp(), 35);
        chkNonce(tx.payNonce);

        SqlSessionFactory sqlSessionFactory = ProcessContext.sqlSessionFactory;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            TxMapper merchatMapper = sqlSession.getMapper(TxMapper.class);
            // 现在可以使用 merchatMapper 调用数据库操作方法了

            LambdaQueryWrapper<TxPayReq> query = new LambdaQueryWrapper<>();
            query.eq(TxPayReq::getOutTradeNo, tx.getOutTradeNo());
            query.eq(TxPayReq::getMchid, tx.getMchid());


            List<TxPayReq> lists = merchatMapper.selectList(query);
            if (lists.size() > 0)
                throw new OUT_TRADE_NO_USED(tx.getOutTradeNo());


            String mchtId = tx.getMchid();


            String privateKey4merchat;
            try {
                Merchat mcht = find(Merchat.class, mchtId);
                privateKey4merchat = mcht.getPrivatekey();
            } catch (EntityNotFoundException e) {
                throw new MCH_NOT_EXISTS("");
            }

            //  chkSign(ThreadContext.currHttpParamMap.get(), privateKey4merchat);
            tx.setTxId(getUuid());
            persist(tx);
            return tx;

        }
        // Object tx2=find(TxPayReq.class,tx.getTxId())
        // Transaction tx=new Transaction();
        //  copyProps(tx,tx);

        // public  String accountId="999";
        //  tx.accountId="accidxxx";

        // copyPropertys(tx,tx);
        //   System.out.println("fun svUHdl");

    }

    private static void chkNonce(String nonce) throws UnauthorizedNonceReplay, UnauthorizedNonceNull, UnauthorizedNonceBlank {

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
    private static boolean chkTmstmpTimeWin(String timestamp, long MAX_REQUEST_AGE_MINUTES) throws DateTimeChkException, INVALID_PARAMETER {

        if (isBlank(timestamp))
            throw new INVALID_PARAMETER("pay_timestamp");

        // 最大允许时间差（单位：分钟）
        //  long MAX_REQUEST_AGE_MINUTES = 35;



            OffsetDateTime requestTime = OffsetDateTime.parse(timestamp);
            OffsetDateTime now = OffsetDateTime.now();

            Duration diff = Duration.between(requestTime, now);

            boolean b = !diff.isNegative() && diff.toMinutes() <= MAX_REQUEST_AGE_MINUTES;

            if (b == false)
                throw new DateTimeChkException();


            return b;


    }


    /**
     * http://localhost:8888/v3/pay/transactions/id/123
     */
    @PermitAll
    //  @RolesAllowed({"adm","opr"})
    public static Object transactionsById(Map<String, List<String>> m) {

        //   Map<String, List<String>> m= ThreadContext.currHttpParamMap.get();

        //todo chg to  getSignleReault()
        String id = extractLastPathSegment(ThreadContext.currUrlPath.get());
        SqlSessionFactory sqlSessionFactory = ProcessContext.sqlSessionFactory;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            TxMapper merchatMapper = sqlSession.getMapper(TxMapper.class);
            // 现在可以使用 merchatMapper 调用数据库操作方法了

            LambdaQueryWrapper<TxPayReq> query = new LambdaQueryWrapper<>();
            query.eq(TxPayReq::getTxId, id);


            List<TxPayReq> lists = merchatMapper.selectList(query);
            if (lists.size() == 0)
                throw new EntityNotFoundException(encodeJson(m));
            Object o = lists.get(0);
            System.out.println(encodeJson(o));
            return o;

        }

    }


    /**
     * http://localhost:8888/tx/notify_merchat?id=666
     */
    @PermitAll
    //  @RolesAllowed({"adm","opr"})
    public static Object notify_merchat(Map<String, List<String>> m) throws NoSuchAlgorithmException {

        //   Map<String, List<String>> m= ThreadContext.currHttpParamMap.get();

        String id = m.get("id").get(0);

        TxPayReq tx = find(TxPayReq.class, id);
        if (tx.getNotifyRespStat().equals(Stat.Success))
            return "ok,notify stat already success";
        System.out.println(encodeJson(tx));

        String resp = notifyOnly(tx);
        if (isNotifyRespSuccess(resp)) {
            tx.setNotifyRespStat(Stat.Success);
            merge(tx);

        }
        return tx;


    }


    /**
     * @param tx
     */
    private static String notifyOnly(TxPayReq tx) throws NoSuchAlgorithmException {

        WechatPayNotifyDTO.Amount amt = new WechatPayNotifyDTO.Amount();
        amt.setTotal(tx.getAmount());
        amt.setPayer_total(tx.getAmount());

        WechatPayNotifyDTO dto = new WechatPayNotifyDTO();
        dto.setAmount(amt);
        dto.setOut_trade_no(tx.getOutTradeNo());
        dto.setMchid(tx.getMchid());
        dto.setSuccess_time(now());
        dto.setTrade_state("SUCCESS");
        dto.setTransaction_id(tx.getTxId());


        // 将对象转换为 JSON（你可以替换为真实序列化逻辑）
        String json = convertToJson(dto);

        Merchat mcht = find(Merchat.class, tx.getMchid());
        String privateKey4merchat = mcht.getPrivatekey();

        //----sign algo
        String sign = getSign(json, privateKey4merchat);
        String resp = HttpUti.postHttp(tx.getNotify_url(), json, sign);
        return resp;


    }

    /**
     * {
     * * "code": "SUCCESS",
     * * "message": "成功"
     * * }
     *
     * @param resp
     * @return
     */
    private static boolean isNotifyRespSuccess(String resp) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(resp);

            String code = rootNode.path("code").asText();
            return "SUCCESS".equals(code);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 列表翻页查询
     * http://localhost:8888/v3/pay/transactions?out_trade_no=123
     */
    @PermitAll
    //  @RolesAllowed({"adm","opr"})
    public static Object transactions(TxPayReq qryDto) {

        Map<String, List<String>> m = ThreadContext.currHttpParamMap.get();

        //   String id=extractLastPathSegment( ThreadContext.currUrlPath.get() );
        SqlSessionFactory sqlSessionFactory = ProcessContext.sqlSessionFactory;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            TxMapper mapper = sqlSession.getMapper(TxMapper.class);
            // 现在可以使用 mapper 调用数据库操作方法了

            LambdaQueryWrapper<TxPayReq> query = new LambdaQueryWrapper<>();
            if (qryDto.getOutTradeNo() != null)
                query.eq(TxPayReq::getOutTradeNo, qryDto.getOutTradeNo());
            query.orderByAsc(TxPayReq::getTxId); // 升序must need order where use mssql


            Page<TxPayReq> page = new Page<>(1, 10); // 第1页，每页5条
            IPage<TxPayReq> result = mapper.selectPage(page, query);
            //List<TxPayReq> lists = mapper.selectList(query);
            System.out.println(encodeJson(result));
            return result;

        }

    }

}
