package service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import jakarta.persistence.Table;
import model.Merchat;
import model.NonDto;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Query;
import util.msg.Message;
import util.msg.ToChat;
import util.msg.UserTg;
import util.msg.tgMsgRq;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import static util.store.JpaMgdb.persistMgdb;
import static util.uti.Uti.encodeJson;
import static util.uti.context.ProcessContext.mgdbDb;
import static util.uti.context.ProcessContext.mgdbFactory;
import static util.uti.orm.JpaUti.persist;

public class ImSvc {

    /**
     ** http://localhost:8888/api/sendMessage?chat_id=98765&text=88
     *      *

     * @return
     */
    public static Object sendMessage(tgMsgRq dto) {

      //  Message m=new Message();
        UserTg u=new UserTg();
        u.setId("frmid");
        ToChat tc=new ToChat();
        tc.setId(dto.getChatId());
        Message m=new Message();
        m.setChat(tc);
        m.setFrom(u);
        m.setText(dto.getText());
        //   System.out.println("fun svUHdl");
        persistMgdb(m);
        return  m;
    }




    /**
     * http://localhost:8888/api/botxx/getUpdates
     * @param m
     * @return
     */
    public static Object getUpdates(NonDto m) {

        String collname="msgx";
        MongoCollection<Document> collection = mgdbDb.getCollection(collname);

        List<Document> li=new ArrayList<>();
        // 插入文档
        FindIterable<Document> docs = collection.find().sort(new Document("date", -1)).limit(100);
        for (Document doc : docs) {
            System.out.println(doc.toJson());
            li.add(doc);
        }
        return  li;
    }



    private MongoTemplate mongoTemplate;

    public List<Document> getRecentMsgx(int limit) {
      //  SimpleMongoClientDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(MongoClients.create(uri), "testdb");
        MongoTemplate mongoTemplate = new MongoTemplate(mgdbFactory);

        Query query = new Query()
                .with(Sort.by(Sort.Direction.DESC, "date"))
                .limit(limit);
        return mongoTemplate.find(query, Document.class);
    }

}
