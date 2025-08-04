package util;

import org.hibernate.SessionFactory;
import util.u.Eml;
import util.u.User;

import java.util.List;

import static util.CfgSvs.getSessionFactory;

import static util.uti.orm.JpaUti.getResultList;
import static util.uti.orm.JpaUti.merge;
import static util.uti.orm.TxMng.callInTransaction;

public class PersistenceConfigExample {
    public static void main(String[] args) throws Throwable {
        // **编程式配置 EntityManagerFactory**
//        Map<String, Object> settings = new HashMap<>();
//        settings.put(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:h2:mem:testdb");
//        settings.put(AvailableSettings.JAKARTA_JDBC_DRIVER, "org.h2.Driver");
//        settings.put(AvailableSettings.JAKARTA_JDBC_USER, "sa");
//        settings.put(AvailableSettings.JAKARTA_JDBC_PASSWORD, "");
//        settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect");
//        settings.put(AvailableSettings.HBM2DDL_AUTO, "update"); // 自动更新数据库
//
//        // **创建 PersistenceConfiguration**
//        PersistenceConfiguration config =new  PersistenceConfiguration("cfg1")
//                .managedClass(User.class) // 绑定实体类
//                .properties(settings);
//        // **创建 EntityManagerFactory**
//        EntityManagerFactory emf =config.createEntityManagerFactory();
//  emf.getSchemaManager().create(true);
//        EntityManager em=emf.createEntityManager();


        SessionFactory sessionFactory = getSessionFactory();
//        begin();
//        //    em.persist(new User("Alice"));
//        merge(new User("Bob1"));
//        //    em.persist(new User("Bob"));
//        commit();


        callInTransaction(em -> {
            Eml eml = new Eml();
            eml.emll = "xx@xx.com";
            eml.len = 88;
            User u = new User("bb66");
            u.email = eml;
            merge(u);
            return null;
        });

        // **查询数据**
        String query1 = "SELECT u FROM User u";
        Class cls = User.class;
        List<User> li = getResultList(query1, cls);


        li.forEach(user -> System.out.println("User: " + user.getName()));


        //  emf.close();
    }


}
