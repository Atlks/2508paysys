import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.util.concurrent.RateLimiter;
import com.querydsl.core.annotations.Config;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.*;
import io.javalin.json.JavalinJackson;
import jakarta.persistence.EntityManager;
import model.Merchat;
import model.MerchatMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;
import service.*;
// util.RegMapContext;
import util.model.admin.Admin;
import util.model.secury.ApiAuditLog;
import util.rest.ApiGatewayResponse;
import util.u.USvs;
import util.uti.context.ProcessContext;
import util.rest.RequestHandler;
import util.uti.context.ThreadContext;
import util.uti.orm.TxMng;


// static util.CfgSvs.getSessionFactory;

// static util.store.HbntUtil.persist;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.out;
import static util.algo.GetUti.getFilename;
import static util.algo.GetUti.getUUid;
import static util.algo.JarClassScanner.getPrjPath;
import static util.auth.AuthService.needLoginAuth;
import static util.auth.JwtUtil.validateToken;
import static util.misc.util2026.getStackTraceAsString;
import static util.misc.util2026.setCrossDomain;
import static util.store.AutoIdx.autoCrtIdx;
import static util.store.dbutil.*;
import static util.uti.Uti.encodeJson;
import static util.uti.http.HttpUti.setThrdHttpContext;
import static util.uti.http.HttpUti.toDto;
import static util.uti.http.RegMap.registerMapping;
import static util.uti.orm.JpaUti.persist;
import static util.uti.orm.TxMng.callInTransaction;

@Config
public class AppJvln {


    public static void main(String[] args) throws Exception {

        //---auto crt db
        String jdbcUrl = ProcessContext.config.getString("jdbc.url");
        String dbname = getDatabaseFileName4mysql(jdbcUrl);
        if (isNetDb(jdbcUrl)) {
            //  crtDatabase(jdbcUrl,dbname);

//        if (jdbcUrl.startsWith("jdbc:mysql")) {
//            var db = getDatabaseFileName4mysql(jdbcUrl);
//            crtDatabase(jdbcUrl, db);
//        }
//        //h2 not need crt db
        }


//        callTry(() -> migrateSql());
//
//        //aft sess factry,crt table again by my slf
//        callTry(() -> scanClzCrtTable());


        //todo auto crt idx
        autoCrtIdx();


        //ini conn,cfg
        AppCfg.iniSqlSessionFactory();
        // testadd(sessionFactory);
        AppCfg.cfgOther();


        //   testMbts();
        //  Process.exit()
        //   System.exit(0); // 正常退出，0 表示没有错误
        //  System.exit(1); // 非正常退出，1 表示有错误或异常

        //  MerchatMapper merchatMapper=sqlSessionFactory.mapper(MerchatMapper.class);
        SessionFactory sessionFactory = AppCfg.iniSessionFactory();


        testAddMcht();


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 禁止输出为时间戳

        Javalin app = Javalin.create((JavalinConfig config) -> {
            // config.http.defaultContentType = "application/json; charset=UTF-8";
            config.router.ignoreTrailingSlashes = true;



           config.jsonMapper(new JavalinJackson(mapper,true));


        }).start(8888);

        ProcessContext.appJvl = (app);
        regErrHdlr(app);
        beforex(app);

        afterx(app);


        app.options("/*", ctx -> {
            setCrossDomain(ctx);
            ctx.header("Allow", "GET, POST, PUT, DELETE, OPTIONS");

            ctx.status(204);
            ctx.result(""); // 标准响应体为空
        });

        // 示例路由
        registerMappings(app);

        //
    }

    private static void testAddMcht() {

        try {
            Merchat mcht = new Merchat();
            mcht.setMchtId("m666");
            mcht.privatekey = "key666";

            RequestHandler<EntityManager, Object> entityManagerObjectRequestHandler = em -> {

                //mercht Svs..add(mcht)
                //Merchat m=new Merchat();


                persist(mcht);
                return mcht;
            };
            callInTransaction(entityManagerObjectRequestHandler);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            Admin adm = new Admin();
            adm.uname="adm666";
          //  mcht.privatekey = "key666";

            RequestHandler<EntityManager, Object> entityManagerObjectRequestHandler = em -> {

                persist(adm);
                return adm;
            };
            callInTransaction(entityManagerObjectRequestHandler);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    private static boolean isNetDb(String jdbcUrl) {
        if (jdbcUrl.startsWith("jdbc:mysql")) {
            return true;
        }
        if (jdbcUrl.startsWith("jdbc:postgresql")) {
            return true;
        }

        return false;
    }


    private static void testMbts() {
        SqlSessionFactory sqlSessionFactory = ProcessContext.sqlSessionFactory;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            MerchatMapper merchatMapper = sqlSession.getMapper(MerchatMapper.class);
            // 现在可以使用 merchatMapper 调用数据库操作方法了

            LambdaQueryWrapper<Merchat> query = new LambdaQueryWrapper<>();
            query.eq(Merchat::getMchtId, "666");


            out.println(encodeJson(merchatMapper.selectList(query)));


        }
    }


    private static void registerMappings(Javalin app) {


        app.get("/error", ctx -> {
            throw new RuntimeException("测试异常");
        });
        app.get("/", ctx -> ctx.result("Hello, Javalin!"));
        app.get("/a", ctx -> ctx.result("Hello, Javalin!"));

        rgmp_mchts(app);
        registerMapping("/b", USvs::Hdl1);
        registerMapping("/sv", USvs::SvUHdl);

      //  RegMapContext rmCtx = new RegMapContext();
    //    registerMapping("/sv2", USvs::SvUHdl, rmCtx);

        //   http://localhost:8888/mcht/add?mchtId=123&name=12
        registerMapping("/mcht/add", MchtSvc::SvMcht);
        registerMapping("/tx/payment", TxSvc::payment);
        registerMapping("/apiv1/pay", PaySvc::pay);
        registerMapping("/v3/pay/transactions/native", PaySvc::pay);
        registerMapping("/v3/pay/transactions/id/*", PaySvc::transactionsById);
        registerMapping("/v3/pay/transactions", PaySvc::transactions);
        // registerMapping("/upload", Xsvc::upload);
        registerMapping("/v3/adm/login", AdmSvc::login);
        registerMapping("/v3/adm/add", AdmSvc::add);
        app.get("/v3/adms/id/{id}", AdmSvc::findById);
        app.post("/upload", CommSvc::upload);


//        registerMapping("/exit", Sys::exit);
//        registerMapping("/exit", Sys::exit);   ..
//        registerMapping ("/mp", USvs::mapDtoF1);
        //swtch fun,,just here swt cfg is ok

        app.post("/pst", ctx -> {
            //javalin 如何接受post参数 name？？
            String name = ctx.formParam("name"); // 获取 POST 参数 "name"
            out.println("get pst prm,name=" + name);
            ctx.result("Hello, Javalin!" + name);
        });
    }


    //mcht_ctroler/add()
    private static void rgmp_mchts_add(Javalin app) {

        app.get("/merchats/add", ctx ->
        {

            Merchat m2 = (Merchat) toDto(Merchat.class, ctx);
            RequestHandler<EntityManager, Object> entityManagerObjectRequestHandler = em -> {

                //mercht Svs..add(mcht)
                //Merchat m=new Merchat();
                m2.mchtId = String.valueOf(System.currentTimeMillis());
                m2.name = "nnn";
                persist(m2);
                return m2;
            };
            try {
                callInTransaction(entityManagerObjectRequestHandler);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            ctx.result("Hello, /merchats/add!");

        });

    }

    private static void rgmp_mchts(Javalin app) {

        app.get("/merchats", ctx ->
        {

            ctx.result("Hello, Javalin!");

        });

    }


    private static void regErrHdlr(Javalin app) {
        // app.
        // 注册全局异常处理器 jvlin
        ExceptionHandler<Exception> exceptionExceptionHandler = (e, ctx) -> {

            out.println("---catch by regErrHdlr..");
            out.flush();
            e.printStackTrace();
            System.err.flush();
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                // throw new RuntimeException(ex);
            }
            out.println("---end catch by regErrHdlr..");
            Object rzt = e;

            //cls http conn
            TxMng.closeConn();

            //
            setCrossDomain(ctx);
            ctx.contentType("text/plain; charset=UTF-8");

            String errstr = getStackTraceAsString(e);
            ApiGatewayResponse rsp = new ApiGatewayResponse("");
            rsp.errcode = e.getClass().getName();
            rsp.setMessage(e.getMessage());
            rsp.setMessage_err(e.getMessage());
            rsp.stackTraceStr = errstr;
            rsp.setStatusCode(500);
            ctx.contentType("application/json; charset=UTF-8");
            ctx.status(500).result(encodeJson(rsp));
        };
        app.exception(Exception.class, exceptionExceptionHandler);
    }


    private static void afterx(Javalin app) {
        Handler handlerAfter = ctx -> {
            // close tx

        };
        app.after(handlerAfter);
    }


    /**lg,adtlg,auth,tx,optino,setCrossDomain,iniThrdCtx
     * -限流 限制频率，黑白名单检测
     *
     * @param app
     */
    private static void beforex(Javalin app) {

        Map<String, RateLimiter> limiterMap = new ConcurrentHashMap<>();

//        app.before(ctx -> {
//
//        });

        // Javalin.before()
        Handler handlerBefore = ctx -> {
            //适用于 日志记录、身份验证、请求预处理。 跨域
            out.println("\n\n\n请求前拦截：" + ctx.path());
            setCrossDomain(ctx);


            //--------------限流 限制频率
            String key = ctx.ip(); // 或 ctx.header("Authorization")
            limiterMap.putIfAbsent(key, RateLimiter.create(20));
            if (!limiterMap.get(key).tryAcquire()) {
                ctx.status(429).result("访问频率过高");
            }

            String traceId = getUUid();
            ThreadContext.setTraceId(traceId);
            //------------api adt lg
            apiAdtLg(ctx, traceId);


            //----身份验证放到具体方法，因为使用的注解模式。。比单独配置简单
            //vld tkn 要放在具体方法调用的时候判断，应该需要获取method的anno，可能不需要jwt检测

            //ctx.path() // 返回像 "/mcht/add" 的路径
//            if (needLoginAuth(ctx.path())) {
//                try {
//                    validateToken(ctx);
//                } catch (validateTokenExcptn e) {
//                    throw new RuntimeException(e);
//                }
//            }

            /**
             * 请求前拦截：/sv
             * fun startHttpContextProcs  .....
             */
//            EntityManager em = sessionFactory.createEntityManager();
//            ThreadContext.currEttyMngr.set(em);

        };
        app.before(handlerBefore);
    }

    private static void apiAdtLg(Context ctx, String traceId) throws Exception {
        ApiAuditLog lg=new ApiAuditLog();
        lg.setEndpoint(ctx.path());
        lg.setMethod(ctx.method().toString());
        if(ctx.method().equals(HandlerType.GET))
            lg.setRequestPayload( encodeJson(ctx.queryParamMap()) );
        else if(ctx.method().equals(HandlerType.POST))
            lg.setRequestPayload( encodeJson(ctx.formParamMap()) );

        lg.setIpAddress(ctx.ip());
        lg.setTimestamp(LocalDateTime.now());

        lg.setTraceId(traceId);
        lg.setIpAddress(ctx.ip());
        lg.setUserIdentifier(""); //jwt  ,mchtId
        callInTransaction(em -> {

           persist(lg);
           return lg;
       });
    }


}

//        app.config.accessManager((handler, ctx, permittedRoles) -> {
//            if (!ctx.header("Authorization").equals("valid-token")) {
//                ctx.status(403).result("无权限访问");
//                return;
//            }
//            handler.handle(ctx);
//        });

//            String query1 = "SELECT u FROM User u";
//            Class cls= User.class;
//            List<User> li=  getResultList(query1,cls);
//            System.out.println(encodeJson(li));
//


//            try {
//                T dto = (T) toDto(mp, lambdaMethodParamFirstType);
//                valdt(dto);
//                rzt = fun.apply(dto);
//                commit();
//            } catch (Throwable e) {
//
//                rollback();
//                e.printStackTrace();
//            }finally {
//                closeConn();
//            }