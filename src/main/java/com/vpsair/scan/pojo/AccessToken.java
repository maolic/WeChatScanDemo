package com.vpsair.scan.pojo;

import lombok.Data;

/**
 * @Description :
 * @Author : Shen Yuanfeng
 * @Date: 2020/11/7 3:28
 */

@Data
public class AccessToken {
    private String access_token;
    private Integer expires_in;
}
