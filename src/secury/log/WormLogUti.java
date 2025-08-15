package secury.log;


import java.io.FileWriter;
import java.io.IOException;

import static util.uti.Uti.encodeJson;

/**
 * db  file,,,mgdb
 */
public class WormLogUti {


public  static void logWorm(Object obj,String logfileColl)
{
    String s=encodeJson(obj);
    appendLine(s,logfileColl);

}


    /**WORM 风格日志（只写不改）：
     *
     *不提供修改或删除 API，符合 WORM 原则
     * @param logfileColl  比如  /logx.log
     */
    private static void appendLine(String lineTxt, String logfileColl) {

        try (FileWriter fw = new FileWriter(logfileColl, true)) {
            fw.write(lineTxt + System.lineSeparator());
        } catch (IOException e) {
          //  throw new RuntimeException("写入日志失败: " + logfileColl, e);
        }
    }


    public  static void logWormMgdb(Object obj)
    {
        String s=encodeJson(obj);

    }
}
