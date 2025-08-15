


敏感接口（如登录、下单）建议限流更严格
超限返回 HTTP 429 Too Many Requests
自定义 IP 或 Token 限流器
你可以维护一个 Map<String, RateLimiter>，按 IP 或用户 Token 进行限流：