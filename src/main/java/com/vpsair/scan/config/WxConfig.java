package com.vpsair.scan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description :
 * @Author : Shen Yuanfeng
 * @Date: 2020/11/7 16:03
 */

@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WxConfig {
    private String appId;
    private String appSecret;
    private String jsSafeDomain;
}
