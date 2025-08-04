package util.model.secury;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_audit_log")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 接口路径
    @Column(nullable = false)
    private String endpoint;

    // 请求方法（GET, POST 等）
    private String method;

    // 调用者身份（如用户 ID 或 token）
    private String userIdentifier;

    // 请求参数
    @Lob
    private String requestPayload;

    // 响应结果（可加状态码、结果描述）
    @Lob
    private String responsePayload;

    // 请求 IP
    private String ipAddress;

    // 响应时间（单位：毫秒）
    private Long durationMs;

    // 时间戳
    @CreationTimestamp
    private LocalDateTime timestamp;

    // 可选：追踪 ID
    private String traceId;

    // 可选：接口是否成功调用
    private Boolean success;

    // 可选：应用标识，便于多系统区分
    private String appCode;

    // 其他扩展字段（如 header、用户设备信息等）
}
