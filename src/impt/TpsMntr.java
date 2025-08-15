package impt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import model.TxMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import util.mapper.ApiAuditLogMapper;
import util.model.secury.ApiAuditLog;
import util.uti.context.ProcessContext;

import java.time.LocalDateTime;

public class TpsMntr {


    public long getLastMinuteTps() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMinuteAgo = now.minusMinutes(1);

        LambdaQueryWrapper<ApiAuditLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(ApiAuditLog::getTimestamp, oneMinuteAgo)
                .lt(ApiAuditLog::getTimestamp, now);

        SqlSessionFactory sqlSessionFactory = ProcessContext.sqlSessionFactory;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
           // TxMapper merchatMapper = sqlSession.getMapper(TxMapper.class);

            ApiAuditLogMapper auditLogMapper = sqlSession.getMapper(ApiAuditLogMapper.class);;
            return auditLogMapper.selectCount(wrapper);

        }

    }
}
