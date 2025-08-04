package service;

import jakarta.annotation.security.PermitAll;
import model.Merchat;

import static util.uti.orm.JpaUti.persist;


/**
 * 商户管理
 */
public class MchtSvc {


    /**
     *
     * @param m
     * @return
     */
    public static Object SvMcht(Merchat m) {

        //   System.out.println("fun svUHdl");
        persist(m);
        return  m;
    }

    /**
     *
     * @param m
     * @return
     */
    @PermitAll
    public static Object delMcht(Merchat m) {

        //   System.out.println("fun svUHdl");
        persist(m);
        return  m;
    }
}
