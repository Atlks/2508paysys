package util.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import util.model.secury.ApiAuditLog;

public interface ApiAuditLogMapper extends BaseMapper<ApiAuditLog> {
    // 无需额外方法，使用已有的 BaseMapper 即可
}
