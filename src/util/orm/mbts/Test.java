package util.orm.mbts;

import util.orm.Column;
import util.orm.Table;

import static util.orm.mbts.CrtTblUti.generateCreateTableSQL;

public class Test {


    public static void main(String[] args) {
       


        Column c;
        //  String  sql=generateInsertSql(account);
        // sql=generateUpdateSql(account);
        //  System.out.println(sql);


        Table table = null;
        System.out.println(table);
        System.out.println(generateCreateTableSQL(table));

    }
}
