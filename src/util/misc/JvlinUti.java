package util.misc;

import com.sun.net.httpserver.HttpExchange;
import io.javalin.Javalin;
import io.javalin.http.Context;
import util.uti.context.ProcessContext;
import util.uti.context.ThreadContext;

public class JvlinUti {


    public static String getUidFrmBrsr() {
        Javalin appJvl=  ProcessContext.appJvl;
        Context ctx= ThreadContext.currJvlnContext.get();
//
//        String ip = appJvl.getRemoteAddress().getAddress().getHostAddress();
//        String ua = appJvl.getRequestHeaders().getFirst("User-Agent");
        String ip = ctx.ip(); // 获取客户端 IP
        String ua = ctx.header("User-Agent"); // 获取 UA
        String accept = ctx.header("Accept"); // 获取 Accept 头

        // 防止 null 值拼接
        ua = ua != null ? ua : "";
        accept = accept != null ? accept : "";

     //   String accept ="";  //bcs swaggui tet cant same
        //exchange.getRequestHeaders().getFirst("Accept");
// 生成 hash 指纹（可做匿名追踪，不是绝对唯一）
        String s = ip +"_ua:"+ ua +"__accept:"+accept;
        System.out.println("endfun getUidFrmBrsr() ret=" + s + " ");
        return s;

    }
}
