package util.rest;

import com.sun.net.httpserver.HttpExchange;
import io.javalin.http.ExceptionHandler;
import util.uti.http.ApiRsps;

import static util.uti.Uti.encodeJson;
import static util.uti.http.HttpUti.printStackTrace;
import static util.uti.http.HttpUti.sendErrorResponse;

public class AopFuns {






    static void setExceptionHandler(HttpExchange exchange, Exception ex) {
        ExceptionHandler<Exception> exceptionExceptionHandler = (e, ctx) -> {
            printStackTrace(e);
            Object rzt = e;
            sendErrorResponse(exchange, 500, encodeJson(new ApiRsps(e)));
            //  ctx.status(500).result("服务器内部错误：" + e.getMessage());
        };
        exceptionExceptionHandler.handle(ex,new ContextImp4sdkwb());
    }

}
