package util.store;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import util.orm.MyMetaObjectHandler;
import util.uti.context.ProcessContext;

import java.util.List;

import static util.store.dbutil.getPwdFromJdbcurl;
import static util.store.dbutil.getUnameFromJdbcurl;


public class MbtsUti {


//    qryExmp
//
//    // 动态添加查询条件
//        query.like(StringUtils.isNotBlank(name), User::getName, name)
//            .ge(minAge != null, User::getAge, minAge)

    /** 纯java 代码配置使用mybatisplus，没有spring
     * v25.731
     * ati
     *
     * @param mappersList
     * @return
     * @throws Exception
     */
    public static SqlSessionFactory buildSessionFactory(List<Class> mappersList) throws Exception {

        // properties.put(org.hibernate.cfg.Environment.USER, getUnameFromJdbcurl(jdbcUrl));
        // properties.put(org.hibernate.cfg.Environment.PASS, getPwdFromJdbcurl(jdbcUrl));

        // 1. 配置数据源
        String jdbcUrl= ProcessContext.jdbcUrl;
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(util.store.dbutil.getDvr(jdbcUrl)); // 适用于 MySQL
        dataSource.setUrl( ProcessContext.jdbcUrl);
        dataSource.setUsername(getUnameFromJdbcurl(jdbcUrl));
        dataSource.setPassword(getPwdFromJdbcurl(jdbcUrl));


        // 2. 创建 MyBatis-Plus 配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true); // 开启驼峰命名转换
        configuration.setJdbcTypeForNull(null); // 处理 NULL 值问题
        configuration.setDefaultScriptingLanguage(XMLLanguageDriver.class); // 设置默认 SQL 语言驱动


        // 3. 注册 Mapper（无需 XML）
        for(Class  mp:mappersList)
        {
            configuration.addMapper(mp);
        }
        // configuration.addMapper(UserMapper.class);
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

}
