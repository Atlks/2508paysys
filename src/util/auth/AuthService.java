package util.auth;

import com.sun.net.httpserver.HttpExchange;
import util.uti.context.ProcessContext;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;
import java.util.Set;

import static util.misc.Util2025.*;
import static util.misc.util2026.getcookie;
import static util.rest.JwtAuthenticator.chkHasPermission;
import static util.uti.Uti.EmptyAnnotations;
import static util.uti.Uti.getVFrmMpArr;
import static util.uti.context.ProcessContext.NO_AUTH_PATHS;

public class AuthService {
  //  public static final Set<String> NO_AUTH_PATHS = Set.of("/reg", "/login");

    /**
     * 判断是否需要登录的url    。。格式为  /reg
     *
     * @param requestURI
     * @return * @return `true` 需要登录，`false` 不需要登录
     */
    public static boolean needLoginAuth(URI requestURI) {
        System.out.println("fun nededLogAuth(uri="+requestURI.getPath());
       String path = requestURI.getPath(); // 只取路径部分，不包括查询参数
        boolean b = !NO_AUTH_PATHS.contains(path);
        System.out.println("endfun needLoginAuth().ret="+b);
        return b;
    }


    public  static void chkAuth(io.javalin.http.Context ctx) throws AccessDeniedException, AccessException {

        String path=ctx.path();
        System.out.println("fun chkAuth");
        //auth chk,
     //   String path = exchange.getRequestURI().getPath();
        // ProcessContext.authMap.get(path);
        Annotation[] rolesNeed = getVFrmMpArr(ProcessContext.authMap, path, EmptyAnnotations());

        String token = "";

        chkHasPermission(path, rolesNeed, token);
        System.out.println("endfun chkAuth");
    }

    ///favicon.ico
    public static boolean needLoginAuth(String urlPath) {
        if(urlPath.equals("/favicon.ico"))
            return  false;
        System.out.println("fun nededLogAuth(uri="+urlPath);
        String path = urlPath; // 只取路径部分，不包括查询参数
        boolean b = !NO_AUTH_PATHS.contains(path);

        System.out.println("endfun needLoginAuth().ret="+b);
        return b;
    }

    public  static boolean isLogined(HttpExchange exchange) throws IOException, IsEmptyEx {
        System.out.println("fun isLogined(httpExch="+encodeJsonObj(toExchgDt(exchange)));
        String uname = getcookie("uname", exchange);
        boolean b = !uname.equals("");
        System.out.println("endfun isLogined().ret="+b);
        return b;
        //   return  true;
    }

    public static  boolean isNotLogined(HttpExchange exchange) throws IOException, IsEmptyEx {

        System.out.println("fun isNotLogined(httpExch="+encodeJsonObj(toExchgDt(exchange)));
        String uname = getcookie("uname", exchange);

        boolean b = uname.equals("");
        System.out.println("endfun isLogined().ret="+b);
        return uname.equals("");
        //   return  true;
    }
}
