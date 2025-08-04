package util.u;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import lombok.experimental.UtilityClass;
//import org.springframework.context.support.BeanDefinitionDsl;
import model.Merchat;
import util.msc.Dtoo;

import java.util.Map;

import static util.uti.Uti.encodeJson;
import static util.uti.http.HttpUti.setContentType;
import static util.uti.orm.JpaUti.persist;
@UtilityClass
public class USvs {

//    private static <T> Object hdl1(T mp) {
//        return 2;
//    }

    public void add2()
    {
        System.out.println("add2");

    }
    public void add1()
    {
        System.out.println("add1");

    }

    public static void main(String[] args) {
        System.out.println(dynmFun());
    }
    public Object dynmFun()
    {
        System.out.println("dynmFun");
        return null;
    }


    public static Object Hdl1(Dto dto) throws Exception {
        System.out.println(dto.u);
        return  1;

    }

    @PermitAll
    @jakarta.annotation.security.RolesAllowed({Role.adm,Role.reviewer})
    public static Object auth(Object o) {
        setContentType(MediaType.TEXT_PLAIN + "; charset=UTF-8");

        return "ok";
    }


    //    sv?id=14
    public static Object SvUHdl(User o) {

     //   System.out.println("fun svUHdl");
        persist(o);
        return  o;
    }


    public static Object mapDtoF1(Map o) {
        System.out.println(encodeJson(o));
        return  o;
    }


    @Produces(MediaType.TEXT_PLAIN) // 输出纯文本

    public static Object TxtHdl(Object o) {
        setContentType(MediaType.TEXT_PLAIN + "; charset=UTF-8");

        return "ok";
    }

    public static Object add2(Dtoo o) {
        System.out.println("add2");
        return  o;
    }
    public static Object add1(Dtoo o) {
        System.out.println("add1");
        return  "sss";
    }


}
