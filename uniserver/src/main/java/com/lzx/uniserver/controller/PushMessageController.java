package com.lzx.uniserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.lzx.uniserver.entity.PushMessage;
import com.lzx.uniserver.entity.User;
import com.lzx.uniserver.interceptor.LoginRequired;
import com.lzx.uniserver.service.PushMessageService;
import com.lzx.uniserver.service.UserService;
import com.lzx.uniserver.tool.Result;
import com.lzx.uniserver.tool.TokenUtil;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/push-message")
public class PushMessageController {
    // STEP1：获取应用基本信息
    private static String appId = "DRvhDcbd6d6oK5O2Czc6b6";
    private static String appKey = "xXCKmxWYsk9RAwfXKo9Q45";
    private static String appSecret = "wG26Eus2Ya5K9VIahtfkh2";
    private static String masterSecret = "E98Rz9rEA26Z9NcADyrUd2";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    @Autowired
    private PushMessageService pushMessageService;
    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public List<PushMessage> getList(HttpServletRequest request) {
        LambdaQueryWrapper<PushMessage> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(PushMessage::getCreateTime);
        return pushMessageService.list(lqw);
    }
    @LoginRequired
    @RequestMapping(value="/push", method = RequestMethod.POST)
    public Result push(HttpServletRequest request, @RequestBody PushMessage pushMessage) {
        Result res = new Result();

        String accessToken = request.getHeader("accessToken");
        Claims claims = TokenUtil.parseJWT(accessToken);
        String id = claims.getId();

        User user = userService.getById(Integer.parseInt(id));
        if (!user.getUsername().equals("admin")){
            return res.fail("您没有权限推送消息！");
        }

        LocalDateTime nowTime = LocalDateTime.now();
        pushMessage.setCreateTime(nowTime);
        boolean bsuccess = pushMessageService.save(pushMessage);
        if (bsuccess) {
            try {
                pushMessage(pushMessage.getTitle(), pushMessage.getContent(), pushMessage.getTime());
                return res.success("success");
            }catch (IOException e){
                e.printStackTrace();
                return res.fail(e.getMessage());
            }
        }else {
            return res.fail("推送消息失败！");
        }
    }

    public void pushMessage(String title, String content, int time) throws IOException {

        IGtPush push = new IGtPush(url, appKey, masterSecret);

        Style0 style = new Style0();
        // STEP2：设置推送标题、推送内容
        style.setTitle(title);
        style.setText(content);
        style.setLogo("push.png");  // 设置推送图标
        // STEP3：设置响铃、震动等推送效果
        style.setRing(true);  // 设置响铃
        style.setVibrate(true);  // 设置震动


        // STEP4：选择通知模板
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setStyle(style);


        // STEP5：定义"AppMessage"类型消息对象,设置推送消息有效期等推送参数
        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * time);  // 时间单位为毫秒

        // STEP6：执行推送
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }
}

