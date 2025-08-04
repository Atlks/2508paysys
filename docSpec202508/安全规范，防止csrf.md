

抵御 CSRF 攻击。这是因为 CSRF（跨站请求伪造）利用的是用户在浏览器中的隐式身份凭证（比如 Cookie），而非令牌本身。下面为你解析：

🔍 JWT 与 CSRF 的关系
JWT 是一种 身份令牌，通常由前端携带在请求头中（Authorization: Bearer ...）

如果 JWT 被存储在 localStorage 或 sessionStorage 中，并通过自定义请求头发送，那么 CSRF 风险较低（因为浏览器不会自动携带）

但如果你把 JWT 存在 Cookie 中，并开启了自动发送（含 HttpOnly）——那就又回到了 CSRF 的风险区域

✅ 防止 CSRF 的正确做法
场景	是否容易遭遇 CSRF	建议防护方式
JWT 存 localStorage，手动加 Authorization	❌ 不易被攻击	可选使用 CSRF Token
JWT 存 Cookie + 自动发送	✅ 高风险	必须加 CSRF Token，或启用 SameSite=Strict
登录态基于 Session ID	✅ 高风险	CSRF Token + Referer 校验


此外：

推荐前后端分离架构下 使用 localStorage 存 JWT 并加请求头

如果你必须使用 Cookie（例如为了支持 HttpOnly），建议配置：SameSite=Strict 或 Lax，并搭配 CSRF Token 校验机制

CSRF Token 一般由服务端生成，前端随表单或请求提交，服务端校验后才允许操作

🧠 你这个项目是金融类的接口系统，对安全性要求非常高。我建议你双保险：

JWT 放 localStorage