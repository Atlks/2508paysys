package service;


import io.javalin.http.Context;
import jakarta.persistence.EntityNotFoundException;
import model.NonDto;
import model.auth.RoleType;
import util.auth.JwtUtil;
import util.ex.existUserEx;
import util.model.AdminLoginDto;
import util.model.admin.Admin;
import util.model.usr.dto.OpenIdTokenResponseDto;
import util.serverless.ApiGatewayResponse;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;

import static util.algo.EncodeUtil.encodeMd5;
import static util.auth.AuthService.chkAuth;
import static util.misc.util2026.hopePwdEq;
import static util.uti.Uti.encodeJson;
import static util.uti.orm.JpaUti.find;
import static util.uti.orm.JpaUti.persist;
import static util.uti.orm.TxMng.callInTransaction;


/**
 * 管理员
 * v25.82
 */
public class AdmSvc {


    /**
     * @param arg
     * @return
     * @throws Throwable http://localhost:8888/v3/adm/add?uname=11&pwd=222
     */

    public static Object add(Admin arg) throws Exception {
        chkExistUser(arg);
        Admin u = new Admin();
        u.uname = arg.uname;
        u.setPwd(encodeMd5(arg.getPwd()));
        persist(u);
        return u;
    }


//    public AddAdminHdr(String uname, String pwd) {
//    }


    //
//    org.hibernate.Session session;

    public static boolean chkExistUser(Admin user) throws existUserEx {
//        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);
        //  om.jdbcurl=saveDirUsrs;
        System.out.println(encodeJson(user));


        try {
            Admin jo = find(Admin.class, user.uname);
            if (jo != null)
                throw new existUserEx("uname(" + user.uname + ")");
        } catch (EntityNotFoundException e) {
            // throw new RuntimeException(e);
        }
        return false;


    }


    /**
     * @param dto
     * @return http://localhost:8888/v3/adm/login?user=11&pwd=222
     */

    public static Object login(AdminLoginDto dto) throws Exception {


//        HttpExchange exchange= HttpUti.httpExchangeCurThrd.get();
//        String uidAuto=getUidFrmBrsr(exchange);
//        String cptchInsvr=  Cptch_map.get(uidAuto);
//
//        if(!dto.cptch.equals("666"))
//        {
//            if(dto.cptch.equals(cptchInsvr)==false)
//                throw new CaptchErrEx("");
//        }
        //   String data = "p=" + crdt.getPasswordAsString() + "&slt=" + k.salt;

        Admin admin = find(Admin.class, dto.u);
        hopePwdEq(admin.getPwd(), encodeMd5(dto.pwd));

        OpenIdTokenResponseDto rsp = new OpenIdTokenResponseDto();
        rsp.setAccess_token((String) setLoginTicket(dto));
        return rsp;


    }

    /**
     * @param usr_dto
     * @return
     */

    public static Object setLoginTicket(AdminLoginDto usr_dto) {
        //   util2026.setcookie("admHRZ", usr_dto.username, ApiGateway.httpExchangeCurThrd.get());
        //   util2026.setcookie("adm", EncryUtil.encryptAesToStrBase64(usr_dto.username, EncryUtil.Key4pwd4aeskey), ApiGateway.httpExchangeCurThrd.get());

        var jwtobj = JwtUtil.newToken(usr_dto.u, RoleType.ADMIN);
        return (jwtobj);

        //   return null;
    }


    /**
     * http://localhost:8888/v3/adms/id/{id}
     * http://localhost:8888/v3/adms/id/adm666
     *
     * Context是个接口，是可以接受的
     * @return
     */
    public static Object findById(Context ctx) throws Exception {
        //   int userId = Integer.parseInt(ctx.pathParam("id"));
        chkAuth(ctx);
        String id = ctx.pathParam("id");

        callInTransaction(em -> {

            Admin a = find(Admin.class, id);
            //   ctx.contentType("application/json; charset=UTF-8");
            ctx.json(new ApiGatewayResponse(a));
            return a;
        });

        return null;
    }
}
