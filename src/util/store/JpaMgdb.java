package util.store;

import com.mongodb.client.MongoCollection;
import jakarta.persistence.Table;
import org.bson.Document;

import static util.uti.Uti.encodeJson;
import static util.uti.context.ProcessContext.mgdbDb;

public class JpaMgdb {

    public static void persistMgdb(Object object) {
        String collname=getTableAnno(object);
        MongoCollection<Document> collection = mgdbDb.getCollection(collname);
        Document d=Document.parse(encodeJson(object));
        // 插入文档
        collection.insertOne(d);
    }

    public static void persistMgdb(Object object,String collname) {
      //  String collname=getTableAnno(object);
        MongoCollection<Document> collection = mgdbDb.getCollection(collname);
        Document d=Document.parse(encodeJson(object));
        // 插入文档
        collection.insertOne(d);
    }

    /**
     * 获取实体类的table注解内容
     * @param object
     * @return
     */
    public static String getTableAnno(Object object) {
        if (object == null) return null;

        Class<?> clazz = object.getClass();
        Table tableAnnotation = clazz.getAnnotation(Table.class);

        if (tableAnnotation != null) {
            return tableAnnotation.name();
        }
        //return   t.name();
        return clazz.getSimpleName();
    }
}
