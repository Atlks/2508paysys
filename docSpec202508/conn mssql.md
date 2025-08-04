
use windows login...open user login mode..set sa n pwd


then  navicate can conn...

but app cant conn
# open tcp model

解决方案步骤（图形界面操作）
打开 SQL Server 配置管理器

Win + R → 输入 SQLServerManager13.msc（或相应版本号）回车

找到左侧栏：SQL Server 网络配置 → SQLEXPRESS 的协议

启用 TCP/IP 协议

找到 TCP/IP → 右键 → 启用

同时建议启用 Named Pipes（部分工具需要）

