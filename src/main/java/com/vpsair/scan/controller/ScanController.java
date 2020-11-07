package com.vpsair.scan.controller;

import com.google.gson.Gson;
import com.vpsair.scan.config.WxConfig;
import com.vpsair.scan.pojo.AccessToken;
import com.vpsair.scan.pojo.JSApiTicket;
import com.vpsair.scan.tools.Sign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @Author : Shen Yuanfeng
 * @Date: 2020/11/6 13:49
 */

@Slf4j
@Controller
public class ScanController {

    private Gson gson = new Gson();

    public static String sentGet(String url){
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            //System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;

    }

    @Autowired
    private WxConfig wxConfig;

    @GetMapping("/scan")
    public ModelAndView scan(){
        /**
         * 获取AccessToken
         * 有效7200秒，建议缓存
         */
        String accessToken = sentGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxConfig.getAppId() + "&secret=" + wxConfig.getAppSecret());
        log.info("accessToken={}",accessToken);

        /**
         * 获取jsapiTicket，将获取到的jsapiToken传入签名
         * 有效7200秒，建议缓存
         */
        String jsapiToken = sentGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + gson.fromJson(accessToken, AccessToken.class).getAccess_token() + "&type=jsapi");
        log.info("jsapiToken={}",jsapiToken);

        /**
         * sign方法传入jsapiToken与扫描页面的url
         * 即如果我在http://example.com/scan页面调用微信扫一扫，则url配置为http://example.com/scan
         * 域名需要在微信公众号内“功能设置”的“JS接口安全域名”内配置
         */
        Map<String, String> map = Sign.sign(gson.fromJson(jsapiToken, JSApiTicket.class).getTicket(), wxConfig.getJsSafeDomain() + "/scan");
        map.put("appId", wxConfig.getAppId());

        return new ModelAndView("scan", map);
    }
}
