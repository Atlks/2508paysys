package cfg;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.LoginSession;
import model.Merchat;
import model.MerchatMapper;
import model.TxMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.hibernate.SessionFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import secury.auditlog.PermissionChangeLog;
import secury.auditlog.SystemConfigChangeLog;
import service.TxSvc;
import util.model.LoginLog;
import util.model.OperationLog;
import util.model.admin.Admin;
import util.model.secury.ApiAuditLog;
import util.orm.MyMetaObjectHandler;
import util.store.HbntUtil;
import util.store.dbutil;
import util.uti.context.ProcessContext;
import util.uti.context.ThreadContext;
import util.rest.RequestHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import static util.store.dbutil.*;
import static util.uti.Uti.encodeJson;
import static util.uti.context.ProcessContext.authMap;
import static util.uti.orm.JpaUti.persist;

public class AppCfg {


    public static void cfgOther() {

        List<Annotation> li=new ArrayList<>();
      //  li.add(@RolesAllowed({"adm","opr"})))
        //RolesAllowed
//addAuth("/tx/payment",


        //cfg as anno,,,scan anno ,as mag cfg....
      //  FunctionX<paymentDto,Object> fx= TxSvc::payment;
        String path = "/tx/payment";
        cfgAuth(path, TxSvc::payment);

      //  authMapCfg
        ProcessContext.NO_AUTH_PATHS = Set.of("/favicon.ico", "/reg", "/login", "/tx/payment");
    }

    private static <dtoType> void cfgAuth(String path, RequestHandler<dtoType, Object> fx) {
        Method mthd=fx.getLambdaMethod();
        Annotation[] anno=mthd.getAnnotations();

        authMap.put(path,anno);

    }



    public static MongoDatabase iniMgdb() throws SQLException {
        // 连接 MongoDB（默认本地端口）
        String connectionString = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(connectionString);

        // 获取数据库（不存在则自动创建）
        String mgdb666 = "mgdb666";
        MongoDatabase mgdb_database = mongoClient.getDatabase(
                mgdb666);


       ProcessContext.mgdbDb=mgdb_database;
       ProcessContext.mgClient=mongoClient;

        SimpleMongoClientDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(MongoClients.create(connectionString), mgdb666);
        ProcessContext.mgdbFactory=factory;
        return mgdb_database;
    }


    public static SessionFactory iniSessionFactory() throws SQLException {
        List<Class> li = new ArrayList<>();
        li.add(Merchat.class);
        li.add(Admin.class);
        li.add(ApiAuditLog.class);  li.add(LoginSession.class);
        li.add(PermissionChangeLog.class);
        li.add(SystemConfigChangeLog.class);
        li.add(LoginLog.class); li.add(OperationLog.class);
        String jdbcUrl = ProcessContext.config.getString("jdbc.url");
        String username = ProcessContext.config.getString("jdbc.username");
        SessionFactory sessionFactory = HbntUtil.getSessionFactory(jdbcUrl, li);
        ProcessContext.sessionFactory = sessionFactory;
        ProcessContext.jdbcUrl=jdbcUrl;

        return sessionFactory;
    }



    private static void testadd(SessionFactory sessionFactory) {
        EntityManager em = sessionFactory.createEntityManager();
        ThreadContext.currEttyMngr.set(em);
        // Session session = em.unwrap(Session.class);
        //  currSession.set(session);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        System.out.println("begin");

        Merchat merchat = new Merchat();
        merchat.mchtId = "6667";
        merchat.name = "mmmm";
        persist(merchat, em);


        transaction.commit();
        System.out.println("commit");
    }

    public static void iniSqlSessionFactory() throws Exception {

        List<Class> mappersList=new ArrayList<>();
        mappersList.add(MerchatMapper.class);
        mappersList.add(TxMapper.class);
        ProcessContext.sqlSessionFactory = buildSessionFactory(mappersList);
    }


    /** 纯java 代码配置使用mybatisplus，没有spring
     * v25.731
     * ati
     *
     * @param mappersList
     * @return
     * @throws Exception
     */
    public static SqlSessionFactory buildSessionFactory(List<Class> mappersList) throws Exception {

        String jdbcUrl = ProcessContext.config.getString("jdbc.url");
        ProcessContext.configDb.put("jdbc.url",jdbcUrl);
        String dvr = dbutil.getDvr(jdbcUrl);
        ProcessContext.configDb.put("jdbc.dvr", dvr);
        ProcessContext.configDb.put("jdbc.u",getUnameFromJdbcurl(jdbcUrl));
        ProcessContext.configDb.put("jdbc.p",getPwdFromJdbcurl(jdbcUrl));


        System.out.println(encodeJson(mappersList));
        // properties.put(org.hibernate.cfg.Environment.USER, getUnameFromJdbcurl(jdbcUrl));
        // properties.put(org.hibernate.cfg.Environment.PASS, getPwdFromJdbcurl(jdbcUrl));

        // 1. 配置数据源
      //  String jdbcUrl= ProcessContext.jdbcUrl;
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(dvr); // 适用于 MySQL
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(getUnameFromJdbcurl(jdbcUrl));
        dataSource.setPassword(getPwdFromJdbcurl(jdbcUrl));


        // 2. 创建 MyBatis-Plus 配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true); // 开启驼峰命名转换
        configuration.setJdbcTypeForNull(null); // 处理 NULL 值问题
        configuration.setDefaultScriptingLanguage(XMLLanguageDriver.class); // 设置默认 SQL 语言驱动
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(getDbType(jdbcUrl)));
        configuration.addInterceptor(interceptor);

        // 3. 注册 Mapper（无需 XML）
        for(Class  mp:mappersList)
        {
            configuration.addMapper(mp);
        }
      // configuration.addMapper(Merchat.class);
        // configuration.table-auto" value="update"  for spr app
        //   configuration.setMetaObjectHandler(new MyMetaObjectHandler()); // 注册 MetaObjectHandler
        // 3. 创建 GlobalConfig 并注册 MetaObjectHandler
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());


        // 3. 设置环境（**避免 NullPointerException**）
        Environment environment = new Environment("development", new JdbcTransactionFactory(), dataSource);
        configuration.setEnvironment(environment);


        // 3. 构建 `SqlSessionFactory`
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(configuration);
        if (sqlSessionFactory == null) {
            throw new RuntimeException("SqlSessionFactory 初始化失败");
        }
        return sqlSessionFactory;
    }




//    /**
//     * 纯java 代码配置使用mybatisplus，没有spring
//     * 🔹 方法 2：使用 mybatis-enhance-actable 插件
//     * 如果你使用的是 Spring Boot + MyBatis-Plus，可以使用 mybatis-enhance-actable 插件，它可以在 系统启动时自动创建或更新数据库表。你可以参考 CSDN 相关教程 进行配置。
//     *
//     *
//     * 手动实现 基于实体类自动创建表 的功能。可以使用 MyBatis-Plus 的 MetaObjectHandler
//     *
//     * 示例：
//     * @return
//     * @throws Exception
//     */
//    public static SqlSessionFactory buildSessionFactory() throws Exception {
//
//       // properties.put(org.hibernate.cfg.Environment.USER, getUnameFromJdbcurl(jdbcUrl));
//       // properties.put(org.hibernate.cfg.Environment.PASS, getPwdFromJdbcurl(jdbcUrl));
//
//        // 1. 配置数据源
//        String jdbcUrl= ProcessContext.jdbcUrl;
//        PooledDataSource dataSource = new PooledDataSource();
//        dataSource.setDriver(util.store.dbutil.getDvr(jdbcUrl)); // 适用于 MySQL
//        dataSource.setUrl( ProcessContext.jdbcUrl);
//        dataSource.setUsername(getUnameFromJdbcurl(jdbcUrl));
//        dataSource.setPassword(getPwdFromJdbcurl(jdbcUrl));
//
//
//        // 2. 创建 MyBatis-Plus 配置
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setMapUnderscoreToCamelCase(true); // 开启驼峰命名转换
//        configuration.setJdbcTypeForNull(null); // 处理 NULL 值问题
//        configuration.setDefaultScriptingLanguage(XMLLanguageDriver.class); // 设置默认 SQL 语言驱动
//        // 3. 注册 Mapper（无需 XML）
//        configuration.addMapper(UserMapper.class);
//        // configuration.table-auto" value="update"  for spr app
//        //   configuration.setMetaObjectHandler(new MyMetaObjectHandler()); // 注册 MetaObjectHandler
//        // 3. 创建 GlobalConfig 并注册 MetaObjectHandler
//        GlobalConfig globalConfig = new GlobalConfig();
//        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
//
//
//        // 3. 设置环境（**避免 NullPointerException**）
//        Environment environment = new Environment("development", new JdbcTransactionFactory(), dataSource);
//        configuration.setEnvironment(environment);
//
//
//        // 3. 构建 `SqlSessionFactory`
//        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(configuration);
//        if (sqlSessionFactory == null) {
//            throw new RuntimeException("SqlSessionFactory 初始化失败");
//        }
//        return sqlSessionFactory;
//    }
//
//

    }
