package secury.captch;

import com.sun.net.httpserver.HttpExchange;

import io.javalin.http.Context;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import model.NonDto;
import util.annos.NoTx;
import util.annos.NoWarpApiRsps;
import util.annos.Paths;
import util.uti.context.ThreadContext;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static secury.captch.CaptchaGenerator.generate;
import static util.misc.JvlinUti.getUidFrmBrsr;
import static util.misc.util2026.setCrossDomain;
import static util.oo.HttpUti.httpExchangeCurThrd;


//
public class CaptchHdr {

    public static Map<String,String> Cptch_map=new HashMap<>();

    @PermitAll
@NoWarpApiRsps
    @Paths({"/apiv1/captcha","/apiv1/api/captcha"})
    @Produces("image/png")
    @NoTx
    //http://localhost:8888/apiv1/captcha
    public static   Object captcha(NonDto args) throws Exception {
        System.setProperty("jdk.net.spi.nameservice.provider.1", "default");
        CaptchaGenerator.Captcha captcha = generate();
        System.out.println("验证码问题: " + captcha.question);
        System.out.println("答案: " + captcha.answer); // 实际使用时不要返回答案！


      //  HttpExchange exchange= httpExchangeCurThrd.get();
        String uidAuto=getUidFrmBrsr();
        Cptch_map.put(uidAuto, String.valueOf(captcha.answer));
        BufferedImage image = new BufferedImage(100, 40, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 100, 40);
        g.setColor(Color.BLACK);
        g.drawString(captcha.question, 10, 25);
// 将 image 写出为 PNG 返回给前端


        g.dispose();

        // 2. 写入 ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        // 3. 返回 Response
        String contentType = "image/png";


        // 设置 HTTP 响应头

        // 设置跨域响应头
        Context ctx = ThreadContext.currJvlnContext.get();
        setCrossDomain(ctx);

        ctx.header("Content-Disposition", " attachment; filename=\"captcha.png\"");
        ctx.header("Content-Type", "image/png");

        ctx.header("Cache-Control", "no-cache, no-store, must-revalidate");
        ctx.header("Pragma", "no-cache");
        ctx.header("Expires", "0");
        // 设置响应状态码
        ctx.status(200);
        // 设置内容长度（可选）
        ctx.header("Content-Length", String.valueOf(imageBytes.length));

// 写入图片数据
        try (OutputStream os = ctx.res().getOutputStream()) {
            os.write(imageBytes);
        }
        return 0;
    }

    //"C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.1.4.1\plugins\maven\lib\maven3\bin\mvn.cmd" dependency:tree

    //endfun getUidFrmBrsr() ret=0:0:0:0:0:0:0:1_ua:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36__accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7

}
