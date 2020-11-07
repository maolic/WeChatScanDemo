package com.vpsair.scan.pojo;

import lombok.Data;

/**
 * @Description :
 * @Author : Shen Yuanfeng
 * @Date: 2020/11/7 14:37
 */

@Data
public class JSApiTicket {
    private Integer errcode;
    private String errmsg;
    private String ticket;
    private Integer expires_in;
}
