package com.smartmall.auth.wechat;

import com.smartmall.auth.config.WechatProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WechatApiClient {

    private final RestTemplate rest = new RestTemplate();
    private final WechatProperties props;

    public record Code2SessionResp(
            @JsonProperty("openid") String openid,
            @JsonProperty("session_key") String sessionKey,
            @JsonProperty("unionid") String unionid,
            @JsonProperty("errcode") Integer errcode,
            @JsonProperty("errmsg") String errmsg
    ) {}

    public WechatApiClient(WechatProperties props) { this.props = props; }

    public Code2SessionResp code2session(String code) {
        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                props.appid(), props.secret(), code);
        return rest.getForObject(url, Code2SessionResp.class);
    }
}
