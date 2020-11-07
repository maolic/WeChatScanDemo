<html>
<head>
    <title>调用微信扫一扫</title>
</head>
<body>
<button id="scanQRCode" style="width: 100px; height: 100px;">扫码</button>
<br />
<br />
<input id="scan_result" />

<script type='text/javascript' src='http://cdn.staticfile.org/jquery/2.1.1/jquery.min.js'></script>
<script type='text/javascript' src='http://res.wx.qq.com/open/js/jweixin-1.6.0.js'></script>
<script type="text/javascript">
    $(function() {
        wx.config({
            debug : false, //开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId : '${appId}', //必填，公众号的唯一标识
            timestamp : '${timestamp}', // 必填，生成签名的时间戳
            nonceStr : '${nonceStr}', // 必填，生成签名的随机串
            signature : '${signature}', // 必填，签名，见附录1
            jsApiList : [ 'scanQRCode' ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
    });
    $("#scanQRCode").click(function() {
        wx.scanQRCode({
            // 默认为0，扫描结果由微信处理，1则直接返回扫描结果
            needResult : 1,
            desc : 'scanQRCode desc',
            success : function(res) {

                $("#scan_result").val(res.resultStr);

                //商品条形码，取","后面的
                if(res.resultStr.indexOf(",")>=0){ //条形码结果
                    var tempArray = res.resultStr.split(',');
                    var tempNum = tempArray[1];
                    //alert(tempNum);
                }else{ //二维码结果
                    //alert(res.resultStr);
                }
            }
        });
    });
</script>
</body>
</html>