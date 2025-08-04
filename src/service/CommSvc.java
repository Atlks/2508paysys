package service;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static util.algo.GetUti.getFilename;
import static util.algo.JarClassScanner.getPrjPath;

public class CommSvc {

    public static void upload(Context ctx) throws IOException {

        UploadedFile file = ctx.uploadedFile("file");
        if (file == null) {
            ctx.status(400).result("No file uploaded");
            return;
        }


        String pathUpld = "/res/uploads";
        String uploadDir = getPrjPath() + pathUpld;
        // 确保上传目录存在
        Files.createDirectories(Paths.get(uploadDir));  // 确保上传目录存在


        // 生成唯一文件名
        String newFileName = getFilename(uploadDir, "upload_");

        // 保存文件到本地
        File targetFile = new File(uploadDir + "/" + newFileName);
        try (InputStream in = file.content();
             FileOutputStream outx = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                outx.write(buffer, 0, bytesRead);
            }
        }

        ctx.result("File uploaded: " + file.filename());
    }
}
