# WeChatScanDemo

#### 说明
最近需要在微信中调用扫一扫功能，发现网上资料虽然挺多但是也比较杂，这里就简单写一个Demo调用扫一扫功能并返回二维码/条形码数据。
由于是Demo，接口的调用返回数据没有做缓存，在调用微信的api后返回的数据默过期时间为7200秒，本地应进行缓存防止到达日接口调用上限。

Demo使用了SpringBoot 2.2.11版本，具体使用版本基本不受版本限制。这里引入了freemarker, Gson等，实际使用按需即可。相关的配置需要在“application.yml”配置文件填写，这里Demo里的配置信息已做处理，需要使用自己的公众号appid等信息，个人公众号同样可以调用此功能。

#### 必要信息：
- 开发者ID(AppID)
- 开发者密码(AppSecret)
- 需要调用扫一扫功能的JS接口安全域名（jsSafeDomain）

#### 在公众号后台的必要设置：
- 公众号开发信息 -> IP白名单
- 公众号设置 -> 功能设置 -> JS接口安全域名

ScanController中使用了Slf4j日志组件，如果不需要则可删除，其中调用的签名代码来自腾讯给出的Demo，包含php、java、nodejs以及python的示例代码供参考，切记要对获取的accesstoken以及jsapi_ticket进行缓存以确保不会触发频率限制。

http://demo.open.weixin.qq.com/jssdk/sample.zip


#### 实现效果

![](https://s1.ax1x.com/2020/11/07/B5DdP0.gif)
