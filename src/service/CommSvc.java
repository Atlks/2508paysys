package service;

import com.baomidou.mybatisplus.annotation.DbType;
import dtoVo.QryMdlDto;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import jakarta.annotation.security.PermitAll;
import org.hibernate.Session;
import util.uti.context.ProcessContext;
import util.uti.context.ThreadContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

// static com.baomidou.mybatisplus.annotation.DbType.getDbType;
// static com.baomidou.mybatisplus.extension.toolkit.JdbcUtils.getDbType;
import static util.algo.GetUti.getFilename;
import static util.algo.JarClassScanner.getPrjPath;
import static util.store.HbntUtil.getResultListMap;
import static util.store.dbutil.getDbType;

public class CommSvc {



    /**
     * 列表翻页查询
     * http://localhost:8888/v3/models?coll_name=login_log
     */
    @PermitAll
    //  @RolesAllowed({"adm","opr"})
    public static Object models(QryMdlDto qryDto) throws Exception {

        Map<String, List<String>> m = ThreadContext.currHttpParamMap.get();

        //   String id=extractLastPathSegment( ThreadContext.currUrlPath.get() );

        Session currentSession = ProcessContext.sessionFactory.getCurrentSession();
       currentSession.getTransaction().begin();
        String offsetSql =getOffsetSqlStmt(qryDto,getDbType(ProcessContext.jdbcUrl));

                //" offset " + qryDto.offset + " limit " + qryDto.limit;


        String sql = "select * from " + fltTableName(qryDto.collName
        )  + " order by " + qryDto.orderby + offsetSql;
        System.out.println(sql);
        return  getResultListMap(sql, currentSession);
    }


    public static String getOffsetSqlStmt(QryMdlDto dto, DbType dbType) {
        String table = dto.collName;
        int offset = dto.offset;
        int limit = dto.limit;
        String orderBy = dto.orderby != null && !dto.orderby.isEmpty() ? dto.orderby : "id"; // 默认按 id 排序


        String dbtype = dbType.name().toLowerCase();
        if(dbtype.equals("sql_server"))
            dbtype="sqlserver";
        switch (dbtype) {
            case "mysql":
                return String.format(" LIMIT %d OFFSET %d",  limit, offset);

            case "postgresql":
                return String.format(" LIMIT %d OFFSET %d",  limit, offset);

            case "sqlserver":
                return String.format(" OFFSET %d ROWS FETCH NEXT %d ROWS ONLY",
                         offset, limit);

            case "oracle":
                return String.format(
                        "SELECT * FROM (SELECT a.*, ROWNUM rnum FROM %s a WHERE ROWNUM <= %d) WHERE rnum > %d",
                        table, offset + limit, offset);

            default:
                throw new UnsupportedOperationException("不支持的数据库类型: " + dbType);
        }
    }

    /**
     * 过滤特殊字符，只保留英文字母和下划线
     * @param collName
     * @return
     */
    private static String fltTableName(String collName) {

        if (collName == null) {
            return "";
        }
        return collName.replaceAll("[^A-Za-z_]", "");
    }

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
