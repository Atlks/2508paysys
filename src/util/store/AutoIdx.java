package util.store;

import util.uti.context.ProcessContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.lang.System.out;
import static util.store.dbutil.executeUpdate;
import static util.store.dbutil.executeUpdateSlice;

public class AutoIdx {

    public static void autoCrtIdx() {

//         Fun2 rh=(String table,String colname)->{
//             out.println(99);
//         };
        try {
            String jdbcUrl = ProcessContext.config.getString("jdbc.url");

            String u = ProcessContext.config.getString("jdbc.username");
            String p = ProcessContext.config.getString("jdbc.password");

            if (jdbcUrl.contains("postgre")) {
                ctIdxPostgreSchema(jdbcUrl, u, p);
            }
            // String jdbcUrl= ProcessContext.config.getString("jdbc.username");
            if (jdbcUrl.contains("mysql")) {
                ctIdxMysql(jdbcUrl, u, p);
            }
            if (jdbcUrl.contains("sqlserver")) {
                ctIdxSqlsvr(jdbcUrl, u, p);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 加字段类型：再加一列 data_type
     * <p>
     * 加备注信息：需要从 pg_catalog 的 pg_description 表读取
     * <p>
     * 排除系统表：通过 table_schema = 'public' 已经过滤了大部分
     *
     * @param jdbcUrl
     * @param user
     * @param password
     */
    public static void ctIdxPostgreSchema(String jdbcUrl, String user, String password) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
             Statement stmt = conn.createStatement()) {

            String sql = """
                        SELECT table_name, column_name
                        FROM information_schema.columns
                           WHERE table_schema = 'public' and table_catalog='db666'
                        ORDER BY table_name, ordinal_position
                    """;

            ResultSet rs = stmt.executeQuery(sql);

            String currentTable = null;
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                String columnName = rs.getString("column_name");

                if (!tableName.equals(currentTable)) {
                    System.out.println("📁 表名: " + tableName);
                    currentTable = tableName;
                }
                System.out.println("   └── 字段: " + columnName);
                String idxName = "idx_" + tableName + "_" + columnName;
                String sql_crt_idx = "CREATE INDEX IF NOT EXISTS " + idxName + " ON " + tableName + " (" + columnName + ");";
                out.println(sql_crt_idx);
                try {
                    executeUpdate(sql_crt_idx, conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 遍历sql server字段，创建索引
     *
     * @param jdbcUrl
     * @param user
     * @param password
     */
    public static void ctIdxSqlsvr(String jdbcUrl, String user, String password) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
             Statement stmt = conn.createStatement()) {

            String sql = """
                    SELECT TABLE_NAME, COLUMN_NAME
                    FROM INFORMATION_SCHEMA.COLUMNS
                    WHERE TABLE_CATALOG = 'db666' -- 替换为你实际的数据库名
                    ORDER BY TABLE_NAME, ORDINAL_POSITION
                    """;

            ResultSet rs = stmt.executeQuery(sql);

            String currentTable = null;
            while (rs.next()) {

                String tableName = rs.getString("TABLE_NAME");
                String columnName = rs.getString("COLUMN_NAME");
                if (!tableName.equals(currentTable)) {
                    System.out.println("📁 表名: " + tableName);
                    currentTable = tableName;
                }
                System.out.println("   └── 字段: " + columnName);
                String idxName = "idx_" + tableName + "_" + columnName;
                //  String sql_crt_idx="CREATE INDEX IF NOT EXISTS "+idxName+" ON "+tableName+" ("+columnName+");";


                String sql_crt_idx = "IF NOT EXISTS (SELECT name FROM sys.indexes WHERE name = '" + idxName + "') " +
                        "CREATE INDEX " + idxName + " ON " + tableName + " (" + columnName + ");";
                out.println(sql_crt_idx);
                try {
                    executeUpdate(sql_crt_idx, conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void ctIdxMysql(String jdbcUrl, String user, String password) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
             Statement stmt = conn.createStatement()) {

            String sql = """
                        SELECT table_name, column_name
                        FROM information_schema.columns
                           WHERE table_schema = 'db666'  
                        ORDER BY table_name, ordinal_position
                    """;

            ResultSet rs = stmt.executeQuery(sql);

            String currentTable = null;
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                String columnName = rs.getString("column_name");

                if (!tableName.equals(currentTable)) {
                    System.out.println("📁 表名: " + tableName);
                    currentTable = tableName;
                }
                System.out.println("   └── 字段: " + columnName);
                String idxName = "idx_" + tableName + "_" + columnName;
                String sql_crt_idx = "CREATE INDEX  " + idxName + " ON " + tableName + " (" + columnName + ");";
                out.println(sql_crt_idx);
                try {
                    executeUpdateSlice(sql_crt_idx, conn);
                } catch (Exception e) {
                    // e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
