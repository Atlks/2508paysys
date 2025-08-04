项目架构设计与选型说明

项目是核心金融类型项目支付平台

编程语言选型，java 

数据库选型 ，起始postgre，,预计未来可升级到微软sql server
 


rest接口框架 使用javalin

管理端与商户后台身份验证，使用jwt模式
数据读写存储api，使用jpa模型hibernate+mybatis plus，兼容多种数据库，方便升级
日志使用aop拦截器模式
内部权限验证使用java secury标准
事务使用jpa3.2标准

三方对外api选型（下单，订单查询，回调等api ）参考了众多知名api简化

其他金融类模块(账户模型account ，余额balance，流水transaction， 报表statment等） 使用标准系列obieV3（openbank），ios20022规范模式

cache缓存：原则上不使用减少数据错误，少数情况根据实际适当使用
报表统计，原则上使用实时统计聚合查询。尽可能不使用增量更新模式容易数据错误



