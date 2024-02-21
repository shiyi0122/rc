package com.hna.hka.archive.management.system.util;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.*;
import com.gexin.rp.sdk.template.style.Style0;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: WeChatGtRobotAppPush
 * @Author: 郭凯
 * @Description: 个推工具类
 * @Date: 2020/5/20 14:17
 * @Version: 1.0
 */
public class WeChatGtRobotAppPush {

    @SuppressWarnings("all")
    public static String singlePush(String ClientId, String Message, String msg) throws Exception {
        System.out.println("控制端个推加密后数据:" + Message);
        IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", "nPLWMT3N946DndRNFU07W4", "O2khGXzHh760kTSObVJAh4");
        System.out.println("个推id:" + ClientId + "消息:" + Message + "内容为:" + msg);

        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId("8i3wJcgtJe827UgZO4New1");
        template.setAppkey("nPLWMT3N946DndRNFU07W4");
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(Message);

        APNPayload payload = new APNPayload();
        payload.setBadge(1);
        payload.setContentAvailable(1);
        //payload.setSound("default");
        payload.setCategory("");

        //payload.addCustomMsg(Message, Message);
        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(msg));
        //字典模式使用下者
        //payload.setAlertMsg(getDictionaryAlertMsg(Message));

        template.setAPNInfo(payload);

        SingleMessage message = new SingleMessage();
        //message.setOffline(true);//我是否保存到服务器上
        // 离线有效时间，单位为毫秒，可选
        //message.setOfflineExpireTime(24 * 3600 * 1000);//就是在服务器上保存多上时间
        //message.setOfflineExpireTime(1 * 60 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId("8i3wJcgtJe827UgZO4New1");
        target.setClientId(ClientId);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            return "1";
            //System.out.println("手机端调用个推:"+ret.getResponse().toString());
        } else {
            return "0";
            //System.out.println("服务器响应异常");
        }
    }

    //后台结算新版pad
    @SuppressWarnings("all")
    public static String singlePushNew(String ClientId, String Message, String msg) throws Exception {
        System.out.println("控制端个推加密后数据:" + Message);
        IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", "OrvGMDRkxh6PL4kESGm3T3", "lcef9J3w119jhwiPnK2BL2");
        System.out.println("个推id:" + ClientId + "消息:" + Message + "内容为:" + msg);

        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId("3FJAOEHYFX67m5k5EZcjb4");
        template.setAppkey("OrvGMDRkxh6PL4kESGm3T3");
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(Message);

        APNPayload payload = new APNPayload();
        payload.setBadge(1);
        payload.setContentAvailable(1);
        //payload.setSound("default");
        payload.setCategory("");

        //payload.addCustomMsg(Message, Message);
        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(msg));
        //字典模式使用下者
        //payload.setAlertMsg(getDictionaryAlertMsg(Message));

        template.setAPNInfo(payload);

        SingleMessage message = new SingleMessage();
        //message.setOffline(true);//我是否保存到服务器上
        // 离线有效时间，单位为毫秒，可选
        //message.setOfflineExpireTime(24 * 3600 * 1000);//就是在服务器上保存多上时间
        //message.setOfflineExpireTime(1 * 60 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId("3FJAOEHYFX67m5k5EZcjb4");
        target.setClientId(ClientId);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            return "1";
            //System.out.println("手机端调用个推:"+ret.getResponse().toString());
        } else {
            return "0";
            //System.out.println("服务器响应异常");
        }
    }
//自动 robotAppType = 2
    public static String singlePushAuto(String ClientId, String Message, String msg) throws Exception {
        System.out.println("控制端个推加密后数据:" + Message);
        IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", "pcKaL0eqDBAAn05AXbO595", "dAKXIakkjZ6zdq4uKXhkw3");
        System.out.println("个推id:" + ClientId + "消息:" + Message + "内容为:" + msg);

        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId("dU1rfaSCbY6M8zkIPiWdT8");
        template.setAppkey("pcKaL0eqDBAAn05AXbO595");
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(Message);

        APNPayload payload = new APNPayload();
        payload.setBadge(1);
        payload.setContentAvailable(1);
        //payload.setSound("default");
        payload.setCategory("");

        //payload.addCustomMsg(Message, Message);
        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(msg));
        //字典模式使用下者
        //payload.setAlertMsg(getDictionaryAlertMsg(Message));

        template.setAPNInfo(payload);

        SingleMessage message = new SingleMessage();
        //message.setOffline(true);//我是否保存到服务器上
        // 离线有效时间，单位为毫秒，可选
        //message.setOfflineExpireTime(24 * 3600 * 1000);//就是在服务器上保存多上时间
        //message.setOfflineExpireTime(1 * 60 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId("dU1rfaSCbY6M8zkIPiWdT8");
        target.setClientId(ClientId);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            return "1";
            //System.out.println("手机端调用个推:"+ret.getResponse().toString());
        } else {
            return "0";
            //System.out.println("服务器响应异常");
        }
    }


    //管理者app
    @SuppressWarnings("all")
    public static String singlePushApp(String ClientId, String Message, String msg) throws Exception {
        // 设置后，根据cid推送，会返回每个cid的推送结果
        //旧版本推送
//        IGtPush push = new IGtPush("http://api.getui.com/apiex.htm", "qjDrXtWhsFAIde8DnQy0X5", "KUyikm8QdT6gQP1HA1s7d1");
        //新版本推送
        IGtPush push = new IGtPush("http://api.getui.com/apiex.htm", "vFhwUHovmj8Kw6yLVx1mB7", "k4dZFAAWRM6Yibzudmu2O6");

        NotificationTemplate template = getNotificationTemplate(Message,msg);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        //旧版本
//        target.setAppId("25iAGxBJD78socnefRHLQ7");
        //新版本
        target.setAppId("UxIXF636U19RPZuyp88Nl1");
        //根据cid推送，如果为 target.setAlias()为别名推送

        target.setClientId(ClientId);
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
//            System.out.println("手机端调用个推:"+ret.getResponse().toString());
            return "1";

        } else {
            return "0";
            //System.out.println("服务器响应异常");
        }
    }

    /**
     * app群推
     * @param ClientId
     * @param Message
     * @param msg
     * @return
     */
    @SuppressWarnings("all")
    public static String pushToList(List<String> ClientId, String Message, String msg) {
        // 配置返回每个用户返回用户状态，可选
        System.setProperty("gexin_pushList_needDetails", "true");
        // 配置返回每个别名及其对应cid的用户状态，可选
        // System.setProperty("gexin_pushList_needAliasDetails", "true");
        IGtPush push = new IGtPush("http://api.getui.com/apiex.htm", "qjDrXtWhsFAIde8DnQy0X5", "KUyikm8QdT6gQP1HA1s7d1");
        // 透传模板
       // ITemplate template = buildTransmissionTemplate(msg);
        NotificationTemplate template = getNotificationTemplate(Message,msg);

        ListMessage message = new ListMessage();
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        // 配置推送目标
        List<Target> targets = new ArrayList<Target>();
        Target target = null;
        for (String cid : ClientId) {
            target = new Target();
            target.setAppId("25iAGxBJD78socnefRHLQ7");
            target.setClientId(cid);
            targets.add(target);
            // target.setAlias(Alias1);
        }
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message, "任务别名_toApp");
        // String taskId = push.getContentId(message);
        IPushResult ret = push.pushMessageToList(taskId, targets);
        System.out.println(ret.getResponse().toString());
        if (ret != null) {
            return "1";
            //System.out.println("手机端调用个推:"+ret.getResponse().toString());
        } else {
            return "0";
            //System.out.println("服务器响应异常");
        }
    }


    @SuppressWarnings("all")
    public static String singlePushNewPadApp(String ClientId, String Message, String msg) throws Exception {
        System.out.println("控制端个推加密后数据:" + Message);
        System.out.println("个推id:" + ClientId + "消息:" + Message + "内容为:" + msg);
        //新版padAPP使用到的个推
        IGtPush pushN = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", "OrvGMDRkxh6PL4kESGm3T3", "lcef9J3w119jhwiPnK2BL2");
        TransmissionTemplate templateN = new TransmissionTemplate();
        templateN.setAppId("3FJAOEHYFX67m5k5EZcjb4");
        templateN.setAppkey("OrvGMDRkxh6PL4kESGm3T3");
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        templateN.setTransmissionType(2);
        templateN.setTransmissionContent(Message);

        APNPayload payload = new APNPayload();
        payload.setBadge(1);
        payload.setContentAvailable(1);
        //payload.setSound("default");
        payload.setCategory("");

        //payload.addCustomMsg(Message, Message);
        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(msg));
        //字典模式使用下者
        //payload.setAlertMsg(getDictionaryAlertMsg(Message));

        templateN.setAPNInfo(payload);
        SingleMessage message = new SingleMessage();
        //message.setOffline(true);//我是否保存到服务器上
        // 离线有效时间，单位为毫秒，可选
        //message.setOfflineExpireTime(24 * 3600 * 1000);//就是在服务器上保存多上时间
        //message.setOfflineExpireTime(1 * 60 * 1000);
        message.setData(templateN);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId("3FJAOEHYFX67m5k5EZcjb4");
        target.setClientId(ClientId);
        IPushResult ret = null;
        try {
            ret = pushN.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = pushN.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            return "1";
            //System.out.println("手机端调用个推:"+ret.getResponse().toString());
        } else {
            return "0";
            //System.out.println("服务器响应异常");
        }
    }


    public static NotificationTemplate getNotificationTemplate(String Message, String msg) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY（旧版本管理者app中的AppID与AppKey）
//        template.setAppId("25iAGxBJD78socnefRHLQ7");
//        template.setAppkey("qjDrXtWhsFAIde8DnQy0X5");

        //新版管理者app中的AppID与APPkey
        template.setAppId("UxIXF636U19RPZuyp88Nl1");
        template.setAppkey("vFhwUHovmj8Kw6yLVx1mB7");

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(Message);
        style.setText(msg);

        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);
        template.setTransmissionType(1);  // 透传消息接受方式设置，1：立即启动APP，2：客户端收到消息后需要自行处理
        template.setTransmissionContent("请输入您要透传的内容");
        return template;
    }


    public static void main(String[] args) throws Exception {

        System.out.println(singlePushApp("1e334fdeb235273734a7487649603748","管理者APP","请别忘记打卡"));
    }

}
