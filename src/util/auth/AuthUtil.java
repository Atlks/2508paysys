package util.auth;



import util.uti.context.ProcessContext;
import util.uti.context.ThreadContext;

// static cfg.Containr.SecurityContext1;
import static util.serverless.RequestHandler.request_getHeaders;

public class AuthUtil {

    /**
     * jdk mode ,starnd
     * @return
     */
    public  static  String getCurrentUser( ) {
        //rmt 只能在测试环境下使用，因为可能复用
        //rmt 只能在测试环境下使用，因为可能复用 thrd
        if(ProcessContext.isTestMode)
        {
            if(ThreadContext.remoteUser.get()!=null)
                return  ThreadContext.remoteUser.get();
        }


        return "";
        //from jwt get ,better
      //  return SecurityContext1.getCallerPrincipal() != null ? SecurityContext1.getCallerPrincipal().getName() : "";
    }


    /**
     * azure mode ,more easy btr than aws
     * @param k
     * @return
     */
    public static String request_getHeaders_get(String k) {
        return (String) request_getHeaders.get().get(k);
    }
}
