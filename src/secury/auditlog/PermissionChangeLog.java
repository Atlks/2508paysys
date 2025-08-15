package secury.auditlog;



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
@Table(name = "permission_change_log")
public class PermissionChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operatorId;        // 操作人 ID
    private String operatorName;      // 操作人姓名

    private String targetUserId;      // 被操作对象（用户）ID
    private String targetUserName;    // 被操作对象（用户）姓名

    private String roleBefore;        // 原角色（如 ROLE_USER）
    private String roleAfter;         // 新角色（如 ROLE_ADMIN）

    private String actionType;        // 操作类型：授权、撤销、提升、降级等
    private String description;       // 变更说明，例如“提权为管理员”

    private String clientIp;          // 操作者 IP 地址
    private String userAgent;         // 设备信息（浏览器/终端）

    private LocalDateTime changeTime; // 操作时间戳
    private String traceId;           // 调用链追踪 ID
}

