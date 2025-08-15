package secury;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.javalin.http.Context;
import jakarta.persistence.EntityNotFoundException;
import model.LoginSession;
import model.SessMngMapper;
import model.TxMapper;
import model.TxPayReq;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import util.uti.context.ProcessContext;
import util.uti.context.ThreadContext;

import java.util.List;

import static util.StrUti.extractLastPathSegment;
import static util.uti.Uti.encodeJson;
import static util.uti.orm.JpaUti.find;
import static util.uti.orm.JpaUti.merge;

public class SessionMng {


    public static Object list(Object object) {
        //todo chg to  getSignleReault()
        String id = extractLastPathSegment(ThreadContext.currUrlPath.get());
        SqlSessionFactory sqlSessionFactory = ProcessContext.sqlSessionFactory;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SessMngMapper merchatMapper = sqlSession.getMapper(SessMngMapper.class);
            // 现在可以使用 merchatMapper 调用数据库操作方法了

            LambdaQueryWrapper<LoginSession> query = new LambdaQueryWrapper<>();



            List<?> lists = merchatMapper.selectList(query);

          //  System.out.println(encodeJson(o));
            return lists;

        }

    }

    public static Object black(Context ctx) {

        LoginSession sess=find(LoginSession.class,ctx.queryParam("token"));
       sess.enable="no";
       merge(sess);
        return sess;
    }
}
