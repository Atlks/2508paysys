package util.uti.context;

// com.google.protobuf.AbstractProtobufList;
import jakarta.servlet.http.HttpServletRequest;

import static util.uti.context.AuthUti.getCurrUname;
import static util.uti.context.AuthUti.setCurrUname;

public class authT {

    public static void main(String[] args) {
        String uname = "888";
        setCurrUname(uname);

        System.out.println(getCurrUname());


        HttpServletRequest request = null;
        boolean isAdmin = request.isUserInRole("ADMIN");
        System.out.println("当前用户是否是管理员: " + isAdmin);
    }




}
