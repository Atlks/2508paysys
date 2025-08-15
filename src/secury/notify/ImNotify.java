package secury.notify;

import com.mongodb.client.MongoCollection;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.bson.Document;
import util.msg.Message;
import util.msg.ToChat;
import util.msg.UserTg;

import java.sql.SQLException;

import static cfg.AppCfg.iniMgdb;
import static util.store.JpaMgdb.persistMgdb;
import static util.uti.Uti.encodeJson;
import static util.uti.context.ProcessContext.mgdbDb;

/**
 *
 sendEmailNotification()	发送邮件通知，如账单、异常告警
 sendSms()	发送短信验证码或交易提醒
 */
public class ImNotify {

    public static void main(String[] args) throws SQLException {

        iniMgdb();

        // 获取集合（不存在则自动创建）
        MongoCollection<Document> collection = mgdbDb.getCollection("logs");

        // 构造文档对象
        Document doc = new Document("endpoint", "/api/login")
                .append("method", "POST")
                .append("userIdentifier", "user123")
                .append("ipAddress", "192.168.1.100")
                .append("success", true)
                .append("timestamp", System.currentTimeMillis());

        // 插入文档
        collection.insertOne(doc);

        System.out.println("插入成功！");

        UserTg u=new UserTg();
        u.setId("frmid");
        ToChat tc=new ToChat();
        tc.setId("toid");
        Message m=new Message();
        m.setChat(tc);
        m.setFrom(u);
        m.setText("msggggg");

        // 获取集合（不存在则自动创建）
        String collname = "msgs";


        insert(collname, m);

        System.out.println("插入成功！");
        //  mongoClient.close();
    }

    public static void insert(String collname, Object object ) {
        MongoCollection<Document> collection = mgdbDb.getCollection(collname);
        Document d=Document.parse(encodeJson(object));
        // 插入文档
        collection.insertOne(d);
    }


    public static void sendSms(String username,String toAddress,String subject,String messageText) throws MessagingException {
        Session session = null;
        jakarta.mail.  Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(jakarta.mail.Message.RecipientType.TO, InternetAddress.parse(toAddress));
        msg.setSubject(subject);
        msg.setText(messageText);
        persistMgdb(msg);
    }


    public static void sendEml(String username,String toAddress,String subject,String messageText) throws MessagingException {
        Session session = null;
        jakarta.mail.  Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(jakarta.mail.Message.RecipientType.TO, InternetAddress.parse(toAddress));
        msg.setSubject(subject);
        msg.setText(messageText);
        persistMgdb(msg,"emls");
    }


}
