## 微服务系统安全设计说明书

* Authentication and Authorization 认证与授权
* Signature 验签
* Rate Limited 限流
* Data desensitization 数据脱敏


#### Authentication and Authorization 认证与授权
![](images/auth2.png)

1. 名词解释

OAuth在"客户端"与"服务提供商"之间，设置了一个授权层（authorization layer）。"客户端"不能直接访问"服务提供商"，只能先登录授权层，以此将用户与客户端区分开来。"客户端"登录授权层所用的令牌（token），与用户的密码不同。用户可以在登录的时候，指定授权层令牌的权限范围和有效期。
"客户端"登录授权层以后，"服务提供商"根据令牌的权限范围和有效期，向"客户端"开放资源。

2. 场景及解决问题

微服务提供无状态访问的API安全问题，针对单个用户访问资源权限，在使用认证与授权后，一般情况下此用户访问本用户的资源

3. 技术方案

Spring Cloud Security && Oauth2.0

#### Signature 验签

1. 场景及解决问题

对于单个用户的资源访问限制已经使用认证与授权解决，但对于一些重要的资源，例如支付，兑换等资金类交易，需要防止参数篡改问题（实际上并不是必须的）

2. 技术方案

加密算法+盐值

#### Rate Limited 限流

1. 场景及解决问题

防止爬虫或肉鸡恶意攻击网站，对IP限制访问频次

2. 技术方案

IP + Redis

#### Data desensitization 数据脱敏

1. 场景及解决问题

访问用户重要信息泄漏，对一些卡号，手机号等数据进行脱敏

2. 技术方案




#### Reference
1. [微服务架构下的安全认证与鉴权](https://mp.weixin.qq.com/s/x0CZpovseOuofTA_lw0HvA)
2. [理解OAuth 2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)
3. [服务接口API限流 Rate Limit](http://www.cnblogs.com/exceptioneye/p/4783904.html)
4. [RateLimit--使用guava来做接口限流](http://blog.csdn.net/jiesa/article/details/50412027)