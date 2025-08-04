


API 审计日志的实体类时，目标是能够记录用户操作的关键行为，包括访问时间、请求内容、响应结果、调用者身份等，以便后续审计与风控分析。


审计日志实体类字段说明
字段名	类型	描述说明
id	Long	主键，自增 ID
endpoint	String	请求的接口路径，例如 /api/order/create
method	String	请求方法，如 GET、POST
userIdentifier	String	用户标识（ID、用户名、Token 等）
requestPayload	String / Lob	请求体内容（JSON 字符串）
responsePayload	String / Lob	响应体内容（JSON 字符串或错误信息）
ipAddress	String	请求来源 IP 地址
durationMs	Long	接口处理耗时（毫秒）
timestamp	LocalDateTime	接口调用时间戳（自动生成）
traceId	String	调用链追踪 ID（用于分布式追踪）
success	Boolean	接口调用是否成功（true/false）
appCode	String	应用标识（区分多服务）
headers	String / Lob	可选，请求头信息（用于调试或合规）
userAgent	String	可选，终端标识（浏览器、设备类型等）



审计日志存储位置建议
根据系统架构与查询场景，可以选择以下方案：

存储方式	适用场景 & 优点
✅ 数据库（如 PostgreSQL）	适合需要查询统计、与其他业务数据关联的系统；可索引、归档、聚合；主流选择
🧾 文件系统（如本地日志）	可用于轻量系统或临时调试；不方便查询；适合打点日志或调试日志


金融类系统，推荐将审计日志记录到数据库，同时通过异步方式推送到 ELK 或云日志系统做聚合分析——既保证了实时性，也支持合规审计