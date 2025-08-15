package util.model;



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
@Table(name = "login_log")
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String username;
    private String loginIp;
    private String userAgent;

    private String loginLocation; // 可选，如通过 IP 地址解析城市
    private String os;            // 操作系统
    private String browser;       // 浏览器类型

    private Boolean success;      // 是否登录成功
    private String message;       // 登录结果描述，如“密码错误”、“登录成功”等

    private LocalDateTime loginTime;
    private String traceId;       // 可选，日志追踪标识
}

