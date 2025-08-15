package secury;

import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
public class EcsLogEntry {
    // 时间戳（ISO8601格式）
    private Instant timestamp;

    // 事件相关
    private String eventKind;         // 如 "event"
    private String eventCategory;     // 如 "authentication", "process"
    private String eventType;         // 如 "info", "error", "start"
    private String eventAction;       // 如 "user_login", "file_access"
    private String eventOutcome;      // 如 "success", "failure"

    // 主机信息
    private String hostName;
    private String hostIp;

    // 用户信息
    private String userId;
    private String userName;

    // 进程信息
    private String processName;
    private int processPid;

    // 网络信息
    private String sourceIp;
    private String destinationIp;
    private int sourcePort;
    private int destinationPort;

    // 自定义字段
    private Map<String, Object> labels;

    // 日志消息
    private String message;

    // ECS 版本
    private String ecsVersion = "8.11.0";

    // Getters & Setters（可使用 Lombok 简化）
}
