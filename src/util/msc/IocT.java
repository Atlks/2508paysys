package util.msc;

import util.u.USvs;
import util.uti.context.ProcessContext;
import util.rest.RequestHandler;

import static util.uti.context.ProcessContext.*;

public class IocT {

    public static void main(String[] args) throws Throwable {
      //  funMap4httpHdlr.put(FunType.USER_add, USvs:: add1);

        ProcessContext.fun_userAdd=USvs:: add2;
        ProcessContext.fun_userAdd=USvs:: add1;

        call(fun_userAdd,null);


//        FunctionX func = getFun( FunType.USER_add);
//        func.apply(Map.of("k",111));
      //   funMap.get( userAdd).apply(11);
    }

    private static void call(RequestHandler funUserAdd, Object o) throws Throwable {
        fun_userAdd.apply(null);

    }


}
