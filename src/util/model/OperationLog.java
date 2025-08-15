package util.model;


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
@Table(name = "operation_log")
public class OperationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String username;
    private String operationType;
    private String moduleName;
    private String action;
    private String requestUri;
    private String httpMethod;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String requestParams;

    private Integer responseStatus;
    private String clientIp;
    private String userAgent;
    private Long durationMs;
    private LocalDateTime timestamp;

    private String traceId;
    private Boolean success;
    private String remark;
}
