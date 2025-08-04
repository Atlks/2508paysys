package service;

import jakarta.annotation.security.PermitAll;
import model.NonDto;
import model.OpenBankingOBIE.Transaction;
import model.paymentDto;
import util.serverless.ApiGatewayResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static util.algo.GetUti.getFilename;
import static util.algo.JarClassScanner.getPrjPath;
import static util.misc.util2026.copyProps;
import static util.uti.orm.JpaUti.persist;

public class Xsvc {


    @PermitAll
    //  @RolesAllowed({"adm","opr"})
    public static Object upload(NonDto m) throws IOException {

        String pathUpld = "/res/uploads";
        String uploadDir = getPrjPath() + pathUpld;
        // 确保上传目录存在
        Files.createDirectories(Paths.get(uploadDir));  // 确保上传目录存在



        // 生成唯一文件名
        String newFileName = getFilename(uploadDir, "upload_");

        // 处理上传的文件
        // 读取请求体并提取文件内容
//        InputStream inputStream = exchange.getRequestBody();
//        parseSingleFile(inputStream, boundary, uploadDir, newFileName);


        // 发送响应 "/apiv1"+
        String rltpath =pathUpld+"/" + newFileName;
        return  new ApiGatewayResponse(rltpath) ;


    }
}
