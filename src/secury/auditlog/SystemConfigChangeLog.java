package secury.auditlog;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "system_config_change_log")
public class SystemConfigChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operatorId;         // 操作人 ID
    private String operatorName;       // 操作人姓名

    private String configKey;          // 配置项标识，例如 "sms.enabled"
    private String configName;         // 配置项名称说明，例如 "短信通知开关"

    @Column(columnDefinition = "TEXT")
    private String valueBefore;        // 原配置值

    @Column(columnDefinition = "TEXT")
    private String valueAfter;         // 修改后的配置值

    private String changeType;         // 变更类型，例如 "修改"、"启用"、"禁用"
    private String changeSource;       // 来源渠道，如 "管理后台"、"配置中心"、"API调用"

    private String clientIp;           // 操作者 IP 地址
    private String userAgent;          // 设备或客户端标识

    private LocalDateTime changeTime;  // 操作时间戳
    private String traceId;            // 调用链追踪 ID（用于链路分析）
    private String remark;             // 备注说明（可选）
}

